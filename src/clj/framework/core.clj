(ns framework.core
  (:require [framework.server :refer [start-server]]))

(defn -main [& args]
  (start-server))
