(ns mech.web
  (:require [compojure.core :refer [defroutes GET routes]]
            [compojure.route :refer [resources not-found]]))

(defn- wrap-root-index [handler]
  (fn [req]
    (handler
     (update-in req [:uri]
                #(if (= "/" %)
                   "/index.html"
                   %)))))

(defn- not-found-page [req]
  {:status 404
   :headers {"Content-Type" "text/html"}
   :body "File not found."})

(defroutes app
  (-> (routes (resources "/")
              (not-found not-found-page))
      (wrap-root-index)))
