(defproject seo-analyzer "1.0.0-SNAPSHOT"
  :description "The seo-analyzer grades a page's seo characteristics."
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [compojure "0.6.3"]
                 [enlive "1.0.0"]]
  :dev-dependencies [[appengine-magic "0.4.1"]
                     [org.clojars.autre/lein-vimclojure "1.0.0"]
                     [vimclojure/server "2.2.0"]]
  :aot [seo-analyzer.app-servlet])
