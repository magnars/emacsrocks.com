(ns user
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]
            [clojure.pprint :refer [pprint pp print-table]]
            [clojure.repl :refer :all]
            [clojure.reflect]))

(defmacro dump-locals []
  `(clojure.pprint/pprint
    ~(into {} (map (fn [l] [`'~l l]) (reverse (keys &env))))))

(defn list-functions [o]
  (print-table
   (sort-by :name
            (filter :exception-types (:members (clojure.reflect/reflect o))))))
