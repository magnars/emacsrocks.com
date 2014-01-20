(defproject emacs-rocks "0.1.0"
  :description "The webpage for emacsrocks.com"
  :url "http://emacsrocks.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [stasis "0.7.0"]
                 [optimus "0.14.2"]
                 [ring "1.2.1"]
                 [hiccup "1.0.4"]
                 [org.clojure/data.xml "0.0.7"]]
  :ring {:handler emacs-rocks.core/app
         :port 3457}
  :aliases {"build-site" ["run" "-m" "emacs-rocks.core/export"]}
  :profiles {:dev {:plugins [[lein-ring "0.8.7"]]
                   :source-paths ["dev"]}})
