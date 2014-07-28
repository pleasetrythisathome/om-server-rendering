(ns framework.server
  (:require [clojure.string     :as str]
            [clojure.java.io    :as io]
            [clojure.pprint     :refer [pprint]]
            [compojure.core     :as comp :refer [defroutes GET POST context]]
            [compojure.route    :as route]
            [compojure.handler  :as handler]
            [hiccup.core        :as hiccup]
            [ring.middleware.reload :as reload]
            [ring.middleware.anti-forgery :as ring-anti-forgery]
            [clojure.core.match :as match :refer [match]]
            [clojure.core.async :as async :refer [<! <!! >! >!! put! take! chan go go-loop close!]]
            [taoensso.timbre    :as timbre]
            [framework.websocket :refer :all])
  (:use org.httpkit.server
        ring.util.response
        ring.middleware.cors))

(defroutes routes
  (GET "/" []
       (-> "public/index.html"
           io/resource
           slurp))
  (route/resources "/")
  (websocket-context)
  (route/not-found "Not Found"))


(def application (-> routes
                     (ring-anti-forgery/wrap-anti-forgery
                      {:read-token (fn [req] (-> req :params :csrf-token))})
                     handler/site
                     reload/wrap-reload
                     (wrap-cors
                      :access-control-allow-origin #".+")))

(defonce server (atom {}))

(defn start-server []
  (let [port (Integer/parseInt (or (System/getenv "PORT")
                                   "8080"))]
    (reset! server (run-server application {:port port
                                            :join? false}))))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn restart-server
  []
  (stop-server)
  (start-server))

(comment
  (start-server)
  (restart-server)
  )
