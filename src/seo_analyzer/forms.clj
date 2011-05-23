(ns seo-analyzer.forms
  (:use [form-dot-clj.core]
        [form-dot-clj.html-controls]))

(def-field keyword-phrase)
(def-field url [:url "Is that a valid url?"])

(def-form analyze-form
          {:size 60 :required "We need this to proceed"}
          :keyword-phrase (textbox keyword-phrase)
          :url (textbox url))

(defn show-analyze-form
  [params errors]
  (str
    (show-controls analyze-form params errors)
    (default-submit "Analyze")))
