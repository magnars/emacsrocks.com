(ns emacs-rocks.pages
  (:require [emacs-rocks.page.index :refer [render-index-page]]))

(defn create-pages [content]
  {"/" {:title "Emacs Rocks!"
        :body (render-index-page content)}})
