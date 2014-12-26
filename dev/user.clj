(ns user
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer [pprint pp print-table]]
            [clojure.reflect]
            [clojure.repl :refer :all]
            [clojure.set :as set]
            [clojure.string :as str]
            [emacs-rocks.system]
            [quick-reset.core :refer [stop reset system]]))

(quick-reset.core/set-constructor 'emacs-rocks.system/create-system)
