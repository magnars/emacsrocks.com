(ns emacs-rocks.page.extending
  (:require [hiccup.core :refer [html]]
            [emacs-rocks.page.index :refer [episode-link]]))

(defn render-episode-page [content episode next]
  (html
   [:div {:id "episode"}
    [:h1 "Extending Emacs Rocks! "
     [:span.nowrap "Episode " (:number episode)]]
    [:div.video-embed
     [:iframe {:src (str "http://www.youtube.com/embed/" (:youtube episode) "?hd=1")
               :frameborder "0"
               :allowfullscreen true}]]
    [:div {:id "download"}
     [:a {:href (str "http://dl.dropbox.com/u/3615058/emacsrocks/extending-emacs-rocks-" (:number episode) ".mov" "?dl=1")}
      "Download this episode"]
     " (" (:size episode) ")"]
    [:div {:id "commits"}
     "In this episode: "
     (map (fn [commit]
            [:p [:a {:href (str "https://github.com/magnars/buster-mode/commit/" (:hash commit))}
                 (:hash commit)]
             (:msg commit)])
          (:commits episode))]
    [:div {:id "github"}
     [:a {:href "https://github.com/magnars/buster-mode/"}
      "The buster-mode project on github"] "."]]
   [:div {:id "more"}
    [:div {:id "next"}
     "Want more?"
     [:ul
      (when next
        [:li [:a {:href (episode-link next)} "See episode " (:number next) " of Extending Emacs Rocks"]])
      [:li [:a {:href "/"} "Browse all episodes"]]
      [:li [:a {:href "http://twitter.com/emacsrocks"} "Follow me on twitter"]]]]
    [:div {:id "comments"}
     [:div {:id "disqus_thread"}
      [:script {:type "text/javascript"}
       "var disqus_shortname = 'emacsrocks';"
       "var disqus_identifier = 'extend-episode-" (:number episode) "';"
       "var disqus_url = 'http://emacsrocks.com" (episode-link episode) "';"
       "(function() {"
       "var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;"
       "dsq.src = 'http://' + disqus_shortname + '.disqus.com/embed.js';"
       "(document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);"
       "})();"]]]]))

(defn get-episode-page [content [episode next]]
  [(episode-link episode)
   {:title (str "Extending Emacs Rocks! Episode " (:number episode))
    :body (render-episode-page content episode next)}])

(defn get-extending-pages [content]
  (->> (:extending-episodes content)
       (partition-all 2 1)
       (map #(get-episode-page content %))
       (into {})))
