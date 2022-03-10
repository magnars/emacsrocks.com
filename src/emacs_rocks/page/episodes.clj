(ns emacs-rocks.page.episodes
  (:require [clojure.java.io :as io]
            [emacs-rocks.page.index :refer [episode-link]]
            [hiccup.core :refer [html]]))

(defn render-episode-page [content episode next]
  (html
   [:div {:id "episode"}
    [:h1 "Emacs Rocks! "
     [:span.nowrap "Episode " (:number episode) ": " (:name episode)]]
    [:div.video-embed
     [:iframe {:src (str "https://www.youtube.com/embed/" (:youtube episode) "?hd=1")
               :frameborder "0"
               :allowfullscreen true}]]
    [:div {:id "download"}
     "Want to download this episode? Use youtube-dl, it's great!"]
    [:div {:id "commands"}
     (map #(get-in content [:commands (str "/" % ".html")])
          (:commands episode))]
    [:div {:id "github"}
     [:a {:href "https://github.com/magnars/.emacs.d/"}
      "My .emacs.d on github"] "."]]
   [:div {:id "more"}
    [:div {:id "next"}
     "Want more?"
     [:ul
      (when next
        [:li [:a {:href (episode-link next)} "See episode " (:number next)]])
      [:li [:a {:href "/"} "Browse all episodes"]]
      [:li [:a {:href "http://twitter.com/emacsrocks"} "Follow me on twitter"]]
      [:li "Read my blog: " [:a {:href "http://whattheemacsd.com"} "What the .emacs.d!?"]]]]
    [:div {:id "comments"}
     [:div {:id "disqus_thread"}
      [:script {:type "text/javascript"}
       "var disqus_shortname = 'emacsrocks';"
       "var disqus_identifier = 'episode" (:number episode) "';"
       "var disqus_url = 'http://emacsrocks.com" (episode-link episode) "';"
       "(function() {"
       "var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;"
       "dsq.src = 'http://' + disqus_shortname + '.disqus.com/embed.js';"
       "(document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);"
       "})();"]]]]))


(defn get-episode-page [content [episode next]]
  [(episode-link episode)
   {:title (str "Emacs Rocks! Episode " (:number episode) ": " (:name episode))
    :body (render-episode-page content episode next)}])

(defn get-episode-pages [content]
  (->> (:episodes content)
       (partition-all 2 1)
       (map #(get-episode-page content %))
       (into {})))
