(defproject framework "0.1.0-SNAPSHOT"
  :description "A clojure(script) framework"
  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments "Same as Clojure"}

  :dependencies [;; clojure
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2268"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [org.clojure/core.match "0.2.1"]

                 ;; server
                 [http-kit "2.1.18"]
                 [compojure "1.1.8"]
                 [ring "1.3.0"]
                 [ring/ring-devel "1.1.8"]
                 [ring-cors "0.1.0"]
                 [ring/ring-anti-forgery "0.3.2"]
                 [hiccup "1.0.5"]
                 [com.taoensso/sente "0.14.1"]

                 ;; utils
                 [com.taoensso/encore "1.6.0"]
                 [com.taoensso/timbre "3.2.1"]

                 ;; om
                 [om "0.6.4"]
                 [sablono "0.2.17"]
                 [prismatic/om-tools "0.2.2"]
                 [omdev "0.1.3-SNAPSHOT"]

                 ;; routing
                 [secretary "1.2.0"]
                 [pleasetrythisathome/tao "0.1.5"]

                 ;; dev tools
                 [ankha "0.1.3"]
                 [shodan "0.3.0"]]

  :plugins [[lein-cljsbuild "1.0.3"]]
  :hooks [leiningen.cljsbuild]

  :source-paths ["src/clj"]
  :main framework.core)
