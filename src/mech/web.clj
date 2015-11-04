(ns mech.web
  (:require [compojure.core :refer [defroutes GET]]))

(defn index [req]
  {:stats 200
   :headers {"Content-Type" "text/html"}
   :body "Hello, Mech!"})

(defroutes app
  (GET "/" [] index))
