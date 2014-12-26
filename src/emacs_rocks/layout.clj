(ns emacs-rocks.layout
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [hiccup.page :refer [html5]]
            [optimus.link :as link]))

(defn- serve-to-media-query-capable-browsers [tag]
  (list "<!--[if (gt IE 8) | (IEMobile)]><!-->" tag "<!--<![endif]-->"))

(defn- serve-to-media-query-clueless-browsers [tag]
  (list "<!--[if (lte IE 8) & (!IEMobile)]>" tag "<![endif]-->"))

(defn render-page [page content request]
  (html5 {:lang "en"}
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1.0"}]
    (serve-to-media-query-capable-browsers
     [:link {:rel "stylesheet" :href (link/file-path request "/styles/responsive.css")}])
    (serve-to-media-query-clueless-browsers
     [:link {:rel "stylesheet" :href (link/file-path request "/styles/unresponsive.css")}])
    ;;[:link {:href "/atom.xml" :rel "alternate" :title "Zombie CLJ" :type "application/atom+xml"}]
    [:title (:title page)]]
   [:body
    [:script (slurp (io/resource "public/scripts/ga.js"))]
    [:div {:id "main"}
     [:a {:id "logo_link" :href "/"}
      [:img {:src "/images/logo.png" :alt "Emacs Rocks!"}]]
     (:body page)]]))
