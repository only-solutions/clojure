(ns processdata.core
  (:gen-class)
  (:require [clojure.string :as str]))

(use 'clojure.java.io)
(require ['clojure.string :as 'str])

(def readfile (fn [filename]
                 (with-open [r (reader filename)]
                   (str/split-lines (slurp filename)))))

(defn getcolfromline [line col]
  ( (str/split line #" ") col)
  )

(defn getcol [col data]
  (map #(Integer/parseInt %)
       (map #(getcolfromline % col) data) )
  )

(defn sumcoln [contents x]
  (let [
        colsum (reduce + (into [] (getcol x contents)))
        ]
    (println "Column " x " sum is " colsum)
  )
  )

(defn -main
  "Sum columns 1 and 2 in input data file"
  [& args]
  (def contents (readfile "/home/there/testdata.txt"))
  (def colstoprocess [1 2])

  (for [x colstoprocess]
    (sumcoln contents x)
    )
  )