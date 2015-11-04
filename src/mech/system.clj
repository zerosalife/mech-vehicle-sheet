(ns mech.system
  (:require [org.httpkit.server :refer [run-server]]
            [com.stuartsierra.component :as component]
            [mech.web]))

(defn- start-server [handler port]
  (let [server (run-server handler {:port port})]
    (println (str "Started server on localhost:" port))
    server))

(defn- stop-server [server]
  (when server
    (server)))

(defrecord MechGen []
  component/Lifecycle
  (start [this]
    (assoc this :server (start-server #'mech.web/app 9009)))
  (stop [this]
    (stop-server (:server this))
    (dissoc this :server)))

(defn create-system []
  (MechGen.))

(defn -main [& args]
  (.start (create-system)))
