(ns mech.system
  (:require [org.httpkit.server :refer [run-server]]))

(defn response [req]
  {:stats 200
   :headers {"Content-Type" "text/html"}
   :body "Hello! from HTTP Kit"})

(defn -main [& args]
  (run-server response {:port 9009})
  (println "Started server on localhost:9009"))
