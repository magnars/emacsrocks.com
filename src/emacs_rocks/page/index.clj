(ns emacs-rocks.page.index
  (:require [hiccup.core :refer [html]]))

(defn episode-link [episode]
  (if (:commits episode)
    (str "/extend/e" (:number episode) ".html")
    (str "/e" (:number episode) ".html")))

(defn- render-episode [episode]
  (html
   [:li
    [:a {:href (episode-link episode)}
     "@emacsrocks "
     [:span.numb ";; episode " (:number episode)]
     [:span.name "\"" (:name episode) "\""]]]))

(defn- render-extending [episode]
  (html
   [:li
    [:a {:href (episode-link episode)}
     "extending emacs "
     [:span.numb ";; " (:number episode)]
     (map (fn [commit] [:span.name (:msg commit)]) (:commits episode))]]))

(defn render-index-page [content]
  (html
   [:p {:id "welcome_text"}
    "Yes, emacs does rock. And here are some episodes to prove it. "
    "Follow me on "
    [:a {:href "http://twitter.com/emacsrocks"} "@emacsrocks"]
    " for more."]
   [:div.episode_list
    [:ul
     (map render-episode (reverse (:episodes content)))]]
   [:div {:style "clear: both;"}]
   [:div {:id "feeds"}
    [:a {:href "http://twitter.com/emacsrocks"}
     [:img {:src "/images/twitter.png"}]] " "
    [:a {:href "/atom.xml"}
     [:img {:src "/images/rss.png"}]]]
   [:h2 {:class "special_series_heading"}
    "Extending Emacs"]
   [:p
    "Join me and " [:a {:href "http://twitter.com/cjno"} "@cjno"]
    " in this special edition Emacs Rocks series where we extend emacs"
    " with a minor-mode for the " [:a {:href "http://busterjs.org"} "Buster.JS"]
    " testing framework."]
   [:div.episode_list
    [:ul
     (map render-extending (:extending-episodes content))]]
   [:div {:style "clear: both;"}]
   [:h2 {:class "special_series_heading"}
    "What the .emacs.d!?"]
   [:p
    "I've also got a blog about setting up your .emacs.d. In the spirit "
    "of Emacs Rocks! all the posts are very short and instantly useful. "
    [:a {:href "http://whattheemacsd.com"} "Check it out here"]]
   [:div {:style "clear: both;"}]
   [:div {:id "footer"}]))
