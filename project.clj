(defproject mech-vehicle-sheet "0.1.0-SNAPSHOT"
  :description "A procedural mecha generator"
  :url "http://www.github.com/zerosalife/mech-vehicle-sheet"
  :license {:name "Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License"
            :url "http://creativecommons.org/licenses/by-nc-sa/4.0/deed.en_US"}
  :main mech.system
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [http-kit "2.1.18"]]
  :profiles {:dev {:plugins []
                   :dependencies []
                   :source-paths ["dev"]}})
