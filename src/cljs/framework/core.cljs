(ns framework.core
  (:require-macros [cljs.core.match.macros :refer [match]]
                   [cljs.core.async.macros :as asyncm :refer [go go-loop]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [weasel.repl :as repl]
            [cljs.core.match]
            [cljs.core.async :as async :refer [<! >! put! chan]]
            [framework.websocket :refer [chsk ch-chsk chsk-send! chsk-state]]))

(enable-console-print!)

;; connect to weasel repl
(when-not (repl/alive?)
  (repl/connect "ws://localhost:9001" :verbose true))

(def app-state (atom {:text "Hello world!"}))

(om/root
  (fn [app owner]
    (dom/h1 nil (:text app)))
  app-state
  {:target (. js/document (getElementById "app"))})
