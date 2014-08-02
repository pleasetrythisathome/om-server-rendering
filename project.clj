(defproject framework "0.1.0-SNAPSHOT"
  :description "A clojure(script) framework"
  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments "Same as Clojure"}

  :dependencies [;; clojure
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2227"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]

                 ;; server
                 [http-kit "2.1.18"]
                 [compojure "1.1.8"]
                 [ring "1.3.0"]
                 [ring/ring-devel "1.1.8"]
                 [hiccup "1.0.5"]
                 [cider/cider-nrepl "0.7.0-SNAPSHOT"]

                 ;; om
                 [om "0.6.4"]
                 [sablono "0.2.17"]
                 [ankha "0.1.3"]
                 [shodan "0.3.0"]]

  :plugins [[lein-cljsbuild "1.0.3"]]
  :hooks [leiningen.cljsbuild]

  :cljsbuild {:builds [{:source-paths ["src/cljs"]
                  :compiler {:preamble ["react/react.min.js"]
                             :output-to "resources/public/framework.js"
                             :output-dir "resources/public/out"
                             :source-map "resources/public/framework.js.map"
                             :optimizations :whitespace}}]}

  :source-paths ["src/clj"]
  :resource-paths ["resources"]
  :main framework.core)
