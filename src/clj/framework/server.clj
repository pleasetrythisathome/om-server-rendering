(ns framework.server
  (:require [clojure.string     :as str]
            [clojure.java.io    :as io]
            [clojure.pprint     :refer [pprint]]
            [compojure.core     :as comp :refer [defroutes GET]]
            [compojure.route    :as route]
            [compojure.handler  :as handler]
            [ring.middleware.reload :as reload]
            [framework.render :as render])
  (:use org.httpkit.server
        ring.util.response))

(defn basic-handler
  "An example req handler that returns a dummy application state based on the supplied request.
  In a fully featured application, this would mirror frontend routing (idealy through shared code)."
  [{:keys [uri] :as req}]
  {:uri uri
   :msg "This state was generated on the server!"})

(defn server-renderer
  "Takes a route->state handler function.
  Returns a wildcard Ring route for server-side rendering the Om frontend."
  [handler]
  (let [renderer (render/render-fn)]
    (GET "*" req
         (let [state (handler req)]
           {:status 200
            :headers {"Content-Type" "text/html; charset=utf-8"}
            :body (renderer state)}))))

(defroutes routes
  (route/resources "/") ;; Server static resources
  (server-renderer basic-handler)
  (route/not-found "Not Found"))


(def application (-> routes
                     handler/site
                     reload/wrap-reload))

(defonce server (atom {}))

(defn start-server
  "Starts the application server."
  []
  (let [port (Integer/parseInt (or (System/getenv "PORT")
                                   "8080"))]
    (reset! server (run-server application {:port port
                                            :join? false}))))

(defn stop-server
  "Stops the application server."
  []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn restart-server
  "Restarts the application server."
  []
  (stop-server)
  (start-server))
