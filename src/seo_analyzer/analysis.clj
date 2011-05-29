(ns seo-analyzer.analysis
  (:use [net.cgrand.enlive-html])
  (:require [appengine-magic.core :as ae]))

(defn fetch-url
  [url]
  (html-resource (java.net.URL. url)))

(defn fetch-test-url
  "Just grab the html out of resources/test/test.html."
  [url]
  (html-resource (ae/open-resource-stream "test/test.html")))

(defn
  ^{:doc "The entire keyword phrase should be in the title."
    :title "Keyword phrase in title"}
  title-tag-contains-keyword
  [html keyword-phrase]
  true)

(defn
  ^{:doc "There should be no repeated words in the title."
    :title "No repeats in title"}
  title-tag-no-repeats
  [html keyword-phrase]
  true)

(defn
  ^{:doc "The title tag should be 6-12 words long."
    :title "Title tag length"}
  title-tag-length
  [html keyword-phrase]
  false)

(def tests [title-tag-contains-keyword title-tag-no-repeats title-tag-length])

(defn analyze-page
  [url keyword-phrase]
  (let [html (fetch-test-url url)]
    (map #(vector
            (let [{:keys [doc title]} (meta %)] {:doc doc :title title})
            (% html keyword-phrase)) tests)))
