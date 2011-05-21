(ns seo-analyzer.core
  (:require [appengine-magic.core :as ae]))


(defn seo-analyzer-app-handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello, world!"})


(ae/def-appengine-app seo-analyzer-app #'seo-analyzer-app-handler)
