(ns emacs-rocks.system
  (:require [emacs-rocks.web :as web]
            [prone.middleware :as prone]
            [emacs-rocks.server :as server]))

(defn start
  "Performs side effects to initialize the system, acquire resources,
  and start it running. Returns an updated instance of the system."
  [system]
  (let [handler (prone/wrap-exceptions (web/create-app))
        server (server/create-and-start handler :port (:port system))]
    (assoc system
           :handler handler
           :server server)))

(defn stop
  "Performs side effects to shut down the system and release its
  resources. Returns an updated instance of the system."
  [system]
  (when (:server system)
    (server/stop (:server system)))
  (dissoc system :handler :server))

(defn create-system []
  "Returns a new instance of the whole application."
  {:port 3456
   :start start
   :stop stop})

(defn -main [& args]
  (let [system (create-system)]
    (start system)))
