(ns processdata.core
  (:gen-class)
  (:require [clojure.string :as str]))
(use 'clojure.java.io)

(def readfile (fn [filename]
                 (with-open [r (reader filename)]
                   (str/split-lines (slurp filename)))))

(defn getcolfromline [line col]
           ((str/split line #" ") col)
  )

;;(mapv #(= (str (first %)) "a") contents)
;;(filterv #(= (str (first %)) "a") contents)
(defn filterlines [col filterstr data]
  (filterv #(= (str (first %)) filterstr) data)
  )


(defn getcol [col data]
  (map #(Integer/parseInt %)
       (map #(getcolfromline % col) data) )
  )

(defn getcolstr [col data]
       (map #(getcolfromline % col) data)
  )

(defn sumcoln [contents x]
  (let [
        colsum (reduce + (into [] (getcol x contents)))
        ]
    colsum
    ;;(println "Column " x " sum is " colsum)
  )
  )

(defn itemtypes [contents]
  (apply sorted-set (set (into [] (getcolstr 0 contents))))
)

(defn -main
  "Find unique item types in data file and sub-total columns 1 and 2"
  [& args]
  (def contents (readfile "/home/there/testdata.txt"))
  (def colstoprocess [1 2])
  ;;determine unique types in column 0
  (def itemtypesvec (itemtypes contents))

  ;;x is a, b, c
  ;;y is 1,2
  (for [x itemtypesvec y colstoprocess
       :let [linesofitemtype (filterlines 0 x contents)
            sumofitemtype (sumcoln linesofitemtype y )]
        ]
       (println "Type " x "Column " y " sum is " sumofitemtype)
       )
  )
