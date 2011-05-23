(ns seo-analyzer.core
  (:use [compojure.core]
        [net.cgrand.enlive-html])
  (:require [appengine-magic.core :as ae]
            [compojure.route :as route]
            [form-dot-clj.core :as form]
            [seo-analyzer.forms :only [show-analyze-form]]))

(deftemplate home (ae/open-resource-stream "home.html") [title]
             [:title] (content title)
             [:h1] (content title)
             [:#content] (content
                           (show-analyze-form nil nil)))

(defn homepage
  [request]
  (apply str (home "SEO Analyzer")))

(defroutes main-routes
  (GET "/" [] homepage)
  (route/not-found "Page not found"))

(def app-handler
  main-routes)

(ae/def-appengine-app seo-analyzer-app #'app-handler)
