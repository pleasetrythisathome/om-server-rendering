(ns framework.websocket
  (:require  [taoensso.sente :as sente]
             [compojure.core :refer [GET POST context]]))


(let [{:keys [ch-recv send-fn ajax-post-fn ajax-get-or-ws-handshake-fn
              connected-uids]}
      (sente/make-channel-socket! {})]
  (def ring-ajax-post                ajax-post-fn)
  (def ring-ajax-get-or-ws-handshake ajax-get-or-ws-handshake-fn)
  (def ch-chsk                       ch-recv) ; ChannelSocket's receive channel
  (def chsk-send!                    send-fn) ; ChannelSocket's send API fn
  (def connected-uids                connected-uids) ; Watchable, read-only atom
  )

(defn websocket-context []
  (context "/chsk" req
           (GET  "/" [] (ring-ajax-get-or-ws-handshake req))
           (POST "/" [] (ring-ajax-post                req))))
