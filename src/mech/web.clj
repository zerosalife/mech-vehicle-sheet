(ns mech.web
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [resources]]))

(defn- wrap-root-index [handler]
  (fn [req]
    (handler
     (update-in req [:uri]
                #(if (= "/" %)
                   "/index.html"
                   %)))))

(defroutes app
  (-> (resources "/")
      (wrap-root-index)))
