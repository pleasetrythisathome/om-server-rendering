(ns framework.websocket
  (:require-macros [cljs.core.async.macros :as asyncm :refer [go go-loop]])
  (:require [cljs.core.async :as async :refer [<! >! put! chan]]
            [taoensso.sente :as sente :refer [cb-success?]]))

(let [{:keys [chsk ch-recv send-fn state]}
      (sente/make-channel-socket! "/chsk" ; Note the same path as before
       {:type :auto ; e/o #{:auto :ajax :ws}
       })]
  (def chsk       chsk)
  (def ch-chsk    ch-recv) ; ChannelSocket's receive channel
  (def chsk-send! send-fn) ; ChannelSocket's send API fn
  (def chsk-state state)   ; Watchable, read-only atom
  )

(comment
  ;; websocket tests
  (chsk-send! [:some/request-id {:name "Rich Hickey" :type "Awesome"}])
  (go-loop []
           (when-let [msg (<! ch-chsk)]
             (js/alert msg)
             (recur))))
