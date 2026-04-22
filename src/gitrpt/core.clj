(ns gitrpt.core
  (:require [clj-pdf.core :refer [pdf]]
            [clojure.java.shell :refer [sh]]
            [clojure.string :as str])
  (:gen-class))

(defn git [dir & args]
  (let [{:keys [exit out err]} (apply sh "git" (concat args [:dir dir]))]
    (when-not (zero? exit)
      (throw (ex-info (str "git failed: " err) {:args args :exit exit})))
    (str/trim out)))

(defn remote-url [dir]
  (git dir "remote" "get-url" "origin"))

(defn remote-branches [dir]
  (->> (str/split-lines (git dir "branch" "-r"))
       (map str/trim)
       (remove str/blank?)))

(defn last-commits [dir n]
  (->> (str/split-lines (git dir "log" (str "-n" n) "--format=%h||%s||%an||%ar"))
       (remove str/blank?)
       (mapv #(str/split % #"\|\|" 4))))

(defn generate-report [dir output-path]
  (let [url (remote-url dir)
        branches (remote-branches dir)
        commits (last-commits dir 5)]
    (pdf
      [
       {:title  "Git Report"
        :author "gitrpt"
        :size   :a4
        :footer {:text "gitrpt" :align :center}
        }

       [:heading "Git Report"]
       [:spacer]

       [:heading {:style {:size 14}} "Remote URL"]
       [:paragraph url]
       [:spacer]

       [:heading {:style {:size 14}} "Remote Branches"]
       (into [:list] branches)
       [:spacer]

       [:heading {:style {:size 14}} "Last 5 Commits"]
       (into
         [:table {:header      ["Hash" "Message" "Author" "Date"]
                  :widths      [10 50 20 20]
                  :border      true
                  :cell-border true}]
         commits)
       [:spacer]

       ]
      output-path)
    (println (str "Report written to " output-path))))

(defn -main [& args]
  (let [dir (or (first args) ".")
        output-path (or (second args) "report.pdf")]
    (generate-report dir output-path)))
