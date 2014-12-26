(ns emacs-rocks.content
  (:require [clojure.java.io :as io]
            [stasis.core :refer [slurp-directory]]))

(defn- load-edn [name]
  (read-string (slurp (io/resource name))))

(defn load-content []
  {:episodes (load-edn "episodes.edn")
   :extending-episodes (load-edn "extending-episodes.edn")
   :commands (slurp-directory "resources/commands/" #"\.html$")})
