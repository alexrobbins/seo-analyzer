(ns seo-analyzer.analysis
  (:use [net.cgrand.enlive-html]))

(defn title-tag-contains-keyword
  "The entire keyword phrase should be in the title."
  {:title "Keyword phrase in title"}
  [html]
  true)

(defn title-tag-no-repeats
  "There should be no repeated words in the title."
  {:title "No repeats in title"}
  [html]
  true)
