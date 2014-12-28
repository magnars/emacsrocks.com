(ns emacs-rocks.rss
  (:require [clojure.data.xml :as xml]
            [clojure.string :as str]
            [emacs-rocks.page.index :refer [episode-link]]
            [hiccup.core :refer [html]]))

(defn to-id-str [str]
  "Replaces all special characters with dashes, avoiding leading,
   trailing and double dashes."
  (-> (.toLowerCase str)
      (str/replace #"[^a-zA-Z0-9]+" "-")
      (str/replace #"-$" "")
      (str/replace #"^-" "")))

(defn- entry [episode]
  [:entry
   [:title (str (:number episode) ": " (:name episode))]
   [:updated (str (:date episode) "T00:00:00+02:00")]
   [:author [:name "Magnar Sveen"]]
   [:link {:href (str "http://emacsrocks.com" (episode-link episode))}]
   [:id (str "urn:emacsrocks-com:feed:episode:" (:number episode))]
   [:content {:type "html"} (html [:a {:href (str "http://emacsrocks.com" (episode-link episode))}
                                   "See the video"])]])

(defn atom-xml [content]
  (xml/emit-str
   (xml/sexp-as-element
    [:feed {:xmlns "http://www.w3.org/2005/Atom"
            :xmlns:media "http://search.yahoo.com/mrss/"}
     [:id "urn:emacsrocks-com:feed"]
     [:updated
      (str (:date (last (:episodes content))) "T00:00:00+02:00")]
     [:title {:type "text"} "Emacs Rocks!"]
     [:link {:rel "self" :href "http://emacsrocks.com/atom.xml"}]
     (->> (:episodes content)
          reverse
          (map entry))])))

