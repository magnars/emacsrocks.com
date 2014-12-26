(ns emacs-rocks.layout
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [hiccup.page :refer [html5]]))

(defn- serve-to-media-query-capable-browsers [tag]
  (list "<!--[if (gt IE 8) | (IEMobile)]><!-->" tag "<!--<![endif]-->"))

(defn- serve-to-media-query-clueless-browsers [tag]
  (list "<!--[if (lte IE 8) & (!IEMobile)]>" tag "<![endif]-->"))

(defn- head-title [page settings]
  (if-let [title (:title page)]
    (str title " | " (:title settings))
    (:title settings)))

(defn render-page [page content]
  (let [settings (:settings content)]
    (html5
     [:head
      [:meta {:charset "utf-8"}]
      [:meta {:name "viewport"
              :content "width=device-width, initial-scale=1.0"}]
      (serve-to-media-query-capable-browsers
       [:link {:rel "stylesheet" :href "/styles/responsive.css"}])
      (serve-to-media-query-clueless-browsers
       [:link {:rel "stylesheet" :href "/styles/unresponsive.css"}])
      [:link {:href "/atom.xml" :rel "alternate" :title "Zombie CLJ" :type "application/atom+xml"}]
      [:title (head-title page settings)]]
     [:body
      [:script (slurp (io/resource "public/scripts/ga.js"))]
      [:div.main
       [:div.header
        [:a {:href "/"} [:img.logo {:src "/img/logo.png"}]]
        [:p.teaser (:teaser settings)]]
       [:div.body
        (:body page)
        (:footer content)]]])))
