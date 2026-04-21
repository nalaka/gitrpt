(ns gitrpt.core
  (:require [clj-pdf.core :refer [pdf]])
  (:gen-class))

(defn generate-report [output-path]

  (pdf
    [{}
     [:list {:roman true}
      [:chunk {:style :bold} "a bold item"]
      "another item"
      "yet another item"]
     [:phrase "some text"]
     [:phrase "some more text"]
     [:paragraph "yet more text"]]
    output-path))

(defn -main [& args]
  (generate-report "report.pdf"))
