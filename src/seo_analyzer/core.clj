(ns seo-analyzer.core
  (:use [compojure.core]
        [net.cgrand.enlive-html]
        [form-dot-clj.core]
        [form-dot-clj.html-controls]
        [ring.middleware params
                         keyword-params 
                         nested-params]
        [seo-analyzer.analysis :only (analyze-page)])
  (:require [appengine-magic.core :as ae]
            [compojure.route :as route]))

;; Setup the form
(def-field keyword-phrase)
(def-field url [:url "Is that a valid url?"])

(def-form analyze-form
          {:size 60 :required "We need this to proceed"}
          :Keyword-Phrase (textbox keyword-phrase)
          :Url (textbox url))

;; Setup the templates
(deftemplate home (ae/open-resource-stream "home.html") [params errors]
             [:form] (html-content
                       (show-controls analyze-form params errors)
                       (default-submit "Analyze")))

(defsnippet result-row (ae/open-resource-stream "results.html")
            [[:tr (nth-of-type 2)]]
            [[{title :title rule :doc} status]]

            [:tr] (set-attr :class (if status "good" "bad"))

            [[:td (nth-of-type 1)]] (content title)
            [[:td (nth-of-type 2)]] (content rule)
            [[:td (nth-of-type 3)]] (content (if status
                                               "OK"
                                               "Bad")))

(deftemplate results-template (ae/open-resource-stream "results.html")
             [keyword-phrase url results]
             [:p] (content (str "Analyzed " url " for \"" keyword-phrase "\"."))
             [[:tr (nth-of-type 2)]] (substitute
                                       (map result-row results))
             [:#reanalyze [:input (attr= :name "Keyword-Phrase")]]
                (set-attr :value keyword-phrase)
             [:#reanalyze [:input (attr= :name "Url")]]
                (set-attr :value url))

(defn results [{keyword-phrase :Keyword-Phrase url :Url}]
  (let [analysis-results (analyze-page keyword-phrase url)]
    (results-template keyword-phrase url analysis-results)))

;; Setup the routes
(defroutes main-routes
  (GET "/" [] (home {} {}))
  (POST "/" {params :params}
        (on-post analyze-form params results home))
  (route/not-found "Page not found"))

(def app-handler
  (-> main-routes
    (wrap-keyword-params)
    (wrap-nested-params)
    (wrap-params)))

(ae/def-appengine-app seo-analyzer-app #'app-handler)
