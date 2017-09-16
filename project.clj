(defproject emacs-rocks "0.1.0"
  :description "The webpage for emacsrocks.com"
  :url "http://emacsrocks.com"
  :jvm-opts ["-Djava.awt.headless=true"]
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [stasis "2.2.2"]
                 [optimus "0.15.1"]
                 [ring "1.3.1"]
                 [enlive "1.1.5"]
                 [hiccup "1.0.4"]
                 [prone "0.8.0"]
                 [org.clojure/data.xml "0.0.7"]]
  :main emacs-rocks.system
  :profiles {:dev {:dependencies [[ciderale/quick-reset "0.1.1"]
                                  [org.clojure/tools.namespace "0.2.8"]]
                   :source-paths ["dev"]}})
