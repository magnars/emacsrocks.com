(ns emacs-rocks.server
  (:require [ring.adapter.jetty :as jetty]))

(defn create-and-start
  [handler & {:keys [port]}]
  {:pre [(not (nil? port))]}
  (jetty/run-jetty handler {:port port :join? false}))

(defn stop
  [server]
  (.stop server))
