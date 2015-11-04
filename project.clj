(defproject mech-vehicle-sheet "0.1.0-SNAPSHOT"
  :description "A procedural mecha generator"
  :url "http://www.github.com/zerosalife/mech-vehicle-sheet"
  :license {:name "Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License"
            :url "http://creativecommons.org/licenses/by-nc-sa/4.0/deed.en_US"}
  :main mech.system
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.145"]
                 [http-kit "2.1.18"]
                 [com.stuartsierra/component "0.2.3"]
                 [compojure "1.4.0"]
                 [environ "1.0.1"]]
  :min-lein-version "2.0.0"
  :uberjar-name "mech-vehicle-sheet.jar"
  :profiles {:uberjar {:source-paths ["env/prod/clj"]
                       :hooks [leiningen.cljsbuild]
                       :env {:production true}
                       :aot :all
                       :main mech.system
                       :cljsbuild {:builds {:app
                                            {:source-paths ["env/prod/cljs"]
                                             :compiler {:optimizations :advanced
                                                        :pretty-print false}}}}}

             :dev {:plugins [[cider/cider-nrepl "0.8.2"]
                             [lein-cljsbuild "1.1.0"]
                             [lein-figwheel "0.4.1"]]
                   :dependencies [[reloaded.repl "0.1.0"]]
                   :source-paths ["dev"]
                                      :cljsbuild {:builds [{:source-paths ["src" "dev"]
                                         :figwheel true
                                         :compiler {:output-to "target/classes/public/app.js"
                                                    :output-dir "target/classes/public/out"
                                                    :main "mech.client"
                                                    :asset-path "/out"
                                                    :optimizations :none
                                                    :recompile-dependents true
                                                    :source-map true}}]}}})
