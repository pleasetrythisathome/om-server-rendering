(ns framework.core
  (:require-macros [cljs.core.match.macros :refer [match]]
                   [cljs.core.async.macros :as asyncm :refer [go go-loop]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [sablono.core :as html :refer-macros [html]]
            [weasel.repl :as repl]
            [goog.dom :as gdom]
            [cljs.core.match]
            [cljs.core.async :as async]
            [cljs.reader :as edn]))

; Print using Nashorn's `print` function if `console` is undefined.
(if (exists? js/console)
  (enable-console-print!)
  (set-print-fn! js/print))

;; connect to weasel repl
;; (when-not (repl/alive?)
;;   (repl/connect "ws://localhost:9001" :verbose true))

(defn app-view [{:keys [uri source] :as data} owner]
  (reify
    om/IRender
    (render [this]
      (html
       [:div
        [:h3
         "Hello server rendering!"]
        [:p
         (str "uri: " uri)]
        [:p
         (str "source: " source)]]))))

(declare app-container
         app-state)

(defn render
  "Renders the app to the DOM.
  Can safely be called repeatedly to rerender the app."[]
  (let [transactions (async/chan)
        transactions-pub (async/pub transactions :tag)]
    (om/root app-view
             app-state
             {:target app-container
              :tx-listen #(async/put! transactions %)
              :shared {:nav-tokens (async/chan)
                       :transactions transactions
                       :transactions-pub transactions-pub}})))

(defn ^:export render-to-string
  "Takes an app state as EDN and returns the HTML for that state.
  It can be invoked from JS as `omelette.view.render_to_string(edn)`."
  [state-edn]
  (->> state-edn
       edn/read-string
       (om/build app-view)
       dom/render-to-str))

(defn ^:export init
  "Initializes the app.
  Should only be called once on page load.
  It can be invoked from JS as `omelette.view.init(appElementId, stateElementId)`."
  [app-id state-id]
  (->> state-id
       gdom/getElement
       .-textContent
       edn/read-string
       atom
       (set! app-state))
  (->> app-id
       gdom/getElement
       (set! app-container))
  (render))
