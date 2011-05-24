(ns seo-analyzer.core
  (:use [compojure.core]
        [net.cgrand.enlive-html]
        [form-dot-clj.core]
        [form-dot-clj.html-controls])
  (:require [appengine-magic.core :as ae]
            [compojure.route :as route]))

(def-field keyword-phrase)
(def-field url [:url "Is that a valid url?"])

(def-form analyze-form
          {:size 60 :required "We need this to proceed"}
          :Keyword-Phrase (textbox keyword-phrase)
          :Url (textbox url))

(deftemplate home (ae/open-resource-stream "home.html") [params errors]
             [:form] (html-content
                       (show-controls analyze-form params errors)
                       (default-submit "Analyze")))

(deftemplate results (ae/open-resource-stream "results.html") [])

(defroutes main-routes
  (GET "/" [] (home {} {}))
  (POST "/" {params :params}
        (on-post analyze-form params results home))
  (route/not-found "Page not found"))

(def app-handler
  main-routes)

(ae/def-appengine-app seo-analyzer-app #'app-handler)
