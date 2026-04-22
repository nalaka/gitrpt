(ns com.github.nalaka.gitrpt.main
  (:require [com.github.nalaka.gitrpt.core :as core])
  (:gen-class))

(defn -main [& args]
  (let [dir (or (first args) ".")
        output-path (or (second args) "report.pdf")]
    (core/generate-report dir output-path)))
