(ns framework.render
  (:require [clojure.java.io :as io]
            [hiccup.page :refer [html5 include-css include-js]]
            [clojure.pprint :refer [pprint]])
  (:import [javax.script
            Invocable
            ScriptEngineManager]))

(defn- render-fn* []
  (let [js (doto (.getEngineByName (ScriptEngineManager.) "nashorn")
             ;; React requires either "window" or "global" to be defined.
             (.eval "var global = this")
             ;; parse the compiled js file
             (.eval (-> "public/framework.js"
                        io/resource
                        io/reader)))
        ;; eval the core namespace
        core (.eval js "framework.core")
        ;; pull the invocable render-to-string method out of core
        render-to-string (fn [edn]
                           (.invokeMethod
                            ^Invocable js
                            core
                            "render_to_string"
                            (-> edn
                                pr-str
                                list
                                object-array)))]
    (fn render [state-edn]
      (html5
       [:head
        [:meta {:charset "utf-8"}]
        [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
        [:meta {:name "viewport" :content "width=device-width"}]
        [:title "Framework"]]
       [:body
        [:noscript "If you're seeing this then you're probably a search engine."]
        (include-js "/framework.js")
        ; Render view to HTML string and insert it where React will mount.
        [:div#framework-app (render-to-string state-edn)]
        ; Serialize app state so client can initialize without making an additional request.
        [:script#framework-state {:type "application/edn"} state-edn]
        ; Initialize client and pass in IDs of the app HTML and app EDN elements.
        [:script {:type "text/javascript"} "framework.core.init('framework-app', 'framework-state')"]]))))

(defn render-fn
  "Returns a function to render fully-formed HTML.
  (fn render [title app-state-edn])"
  []
  (let [pool (ref (repeatedly 3 render-fn*))]
    (fn render [state-edn]
      (let [rendr (dosync
                   (let [f (first @pool)]
                     (alter pool rest)
                     f))
            rendr (or rendr (render-fn*))
            html (rendr state-edn)]
        (dosync (alter pool conj rendr))
        html))))
