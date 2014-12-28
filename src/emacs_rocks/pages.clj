(ns emacs-rocks.pages
  (:require [emacs-rocks.page.episodes :refer [get-episode-pages]]
            [emacs-rocks.page.extending :refer [get-extending-pages]]
            [emacs-rocks.page.index :refer [render-index-page]]
            [stasis.core :refer [merge-page-sources]]))

(defn create-pages [content]
  (merge-page-sources
   {:general-pages {"/" {:title "Emacs Rocks!"
                         :body (render-index-page content)}}
    :episode-pages (get-episode-pages content)
    :extending-pages (get-extending-pages content)}))
