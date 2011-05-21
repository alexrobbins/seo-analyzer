(ns seo-analyzer.app_servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use seo-analyzer.core)
  (:use [appengine-magic.servlet :only [make-servlet-service-method]]))


(defn -service [this request response]
  ((make-servlet-service-method seo-analyzer-app) this request response))
