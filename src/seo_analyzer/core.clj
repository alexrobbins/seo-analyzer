(ns seo-analyzer.core
  (:use compojure.core)
  (:require [appengine-magic.core :as ae]
            [compojure.route :as route]))


(defroutes main-routes
  (GET "/" [] "<h1>SEO Analyzer</h1>")
  (route/not-found "Page not found"))

(def app-handler
  main-routes)

(ae/def-appengine-app seo-analyzer-app #'app-handler)
