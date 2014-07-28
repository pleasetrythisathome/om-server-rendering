(defproject framework "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2268"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [org.clojure/core.match "0.2.1"]

                 [http-kit "2.1.18"]
                 [compojure "1.1.8"]
                 [ring "1.3.0"]
                 [ring/ring-devel "1.1.8"]
                 [ring-cors "0.1.0"]
                 [hiccup "1.0.5"]
                 [com.taoensso/sente "0.14.1"]
                 [com.taoensso.forks/ring-anti-forgery "0.3.1"]
                 [com.taoensso/timbre "3.2.1"]

                 [om "0.6.4"]
                 [sablono "0.2.17"]
                 [secretary "1.2.0"]
                 [pleasetrythisathome/tao "0.1.5"]]

  :plugins [[lein-cljsbuild "1.0.3"]]
  :hooks [leiningen.cljsbuild]

  :source-paths ["src/clj"]
  :main framework.core)
