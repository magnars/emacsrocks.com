(ns emacs-rocks.web
  (:require [emacs-rocks.content :refer [load-content]]
            [emacs-rocks.layout :refer [render-page]]
            [emacs-rocks.pages :refer [create-pages]]
            [emacs-rocks.rss :as rss]
            [net.cgrand.enlive-html :refer [sniptest]]
            [optimus.assets :as assets]
            [optimus.assets.load-css :refer [external-url?]]
            [optimus.export]
            [optimus.link :as link]
            [optimus.optimizations :as optimizations]
            [optimus.prime :as optimus]
            [optimus.strategies :refer [serve-live-assets]]
            [prone.middleware :refer [wrap-exceptions]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [stasis.core :as stasis]))

(defn get-assets []
  (assets/load-assets "public" ["/styles/responsive.css"
                                "/styles/unresponsive.css"
                                "/favicon.ico"
                                #"/images/.+\..+"]))

(defn- optimize-path-fn [request]
  (fn [src]
    (if (or (external-url? src)
            (= "/atom.xml" src))
      src
      (or (not-empty (link/file-path request src))
          (throw (Exception. (str "Asset not loaded: " src)))))))

(defn- use-optimized-assets [html request]
  (sniptest html [:img] #(update-in % [:attrs :src] (optimize-path-fn request))))

(defn- prepare-page [page content request]
  (-> page
      (render-page content request)
      (use-optimized-assets request)))

(defn update-vals [m f]
  (into {} (for [[k v] m] [k (f v)])))

(defn get-pages []
  (let [content (load-content)]
    (-> content
        create-pages
        (update-vals #(partial prepare-page % content))
        (merge {"/atom.xml" (rss/atom-xml content)})
        )))

(def optimize optimizations/all)

(defn create-app []
  (-> (stasis/serve-pages get-pages)
      wrap-exceptions
      (optimus/wrap get-assets optimize serve-live-assets)
      wrap-content-type))

(def export-directory "./build")

(defn- load-export-dir []
  (stasis/slurp-directory export-directory #"\.[^.]+$"))

(defn export []
  (let [assets (optimize (get-assets) {})
        old-files (load-export-dir)]
    (stasis/empty-directory! export-directory)
    (optimus.export/save-assets assets export-directory)
    (stasis/export-pages (get-pages) export-directory {:optimus-assets assets})
    (println)
    (println "Export complete:")
    (stasis/report-differences old-files (load-export-dir))
    (println)))
