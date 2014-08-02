(ns framework.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [cljs.core.async :as async]
            [cljs.reader :as edn]
            [goog.dom :as gdom]

            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [sablono.core :as html :refer-macros [html]]

            [shodan.console :as console :include-macros true]
            [ankha.core :as ankha]))

; Print using Nashorn's `print` function if `console` is undefined.
(if (exists? js/console)
  (enable-console-print!)
  (set-print-fn! js/print))

(defn app-view [{:keys [uri source] :as data} owner]
  (reify
    om/IDidMount
    (did-mount [this]
      (console/log "app-view mounted into DOM"))
    om/IRender
    (render [this]
      (html
       [:div
        [:h3
         "Hello server rendering!"]
        (om/build ankha/inspector data)]))))

(declare app-container
         app-state)

(defn render
  "Renders the app to the DOM.
  Can safely be called repeatedly to rerender the app."[]
  (om/root app-view
           app-state
           {:target app-container}))

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
