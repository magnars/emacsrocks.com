(ns emacs-rocks.core
  (:require [ring.middleware.content-type :refer [wrap-content-type]]
            [stasis.core :as stasis]
            [optimus.assets :as assets]
            [optimus.prime :as optimus]
            [optimus.optimizations :as optimizations]
            [optimus.strategies :refer [serve-live-assets]]
            [optimus.export]))

(defn get-assets []
  (concat
   (assets/load-bundle "public" "/styles.css" ["/styles/reset.css"
                                               "/styles/main.css"])
   (assets/load-assets "public" ["/favicon.ico"
                                 "/images/logo.png"
                                 #"/scripts/.*\.js"])))

(defn get-pages []
  {})

(def app (-> (stasis/serve-pages get-pages)
             (optimus/wrap get-assets optimizations/all serve-live-assets)
             wrap-content-type))

(def export-directory "./build/")

(defn export []
  (let [assets (optimizations/all (get-assets) {})]
    (stasis/empty-directory! export-directory)
    (optimus.export/save-assets assets export-directory)
    (stasis/export-pages (get-pages) export-directory {:optimus-assets assets})))
