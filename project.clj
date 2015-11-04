(defproject mech-vehicle-sheet "0.1.0-SNAPSHOT"
  :description "A procedural mecha generator"
  :url "http://www.github.com/zerosalife/mech-vehicle-sheet"
  :license {:name "Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License"
            :url "http://creativecommons.org/licenses/by-nc-sa/4.0/deed.en_US"}
  :main mech.system
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [http-kit "2.1.18"]
                 [com.stuartsierra/component "0.2.3"]
                 [reloaded.repl "0.1.0"]
                 [compojure "1.4.0"]]
  :profiles {:dev {:plugins [[cider/cider-nrepl "0.8.2"]]
                   :dependencies []
                   :source-paths ["dev"]}})
