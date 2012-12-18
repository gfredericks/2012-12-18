(ns dec-18-2012.euler-185
  "See http://projecteuler.net/problem=185"
  (:refer-clojure :exclude [==])
  (:use clojure.core.logic))

(def digits-domain (apply domain (range 10)))

(defn guesso
  "Succeeds when the correct-count is correct for the given
  guess and string."
  [guess correct-count string]
  (matche [guess string]
          ([[] []] (== 0 correct-count))
          ([[x . xs] [y . ys]]
             (!=fd x y)
             (guesso xs correct-count ys))
          ([[x . xs] [x . ys]]
             (if (zero? correct-count)
               fail
               (guesso xs (dec correct-count) ys)))))

(defn solve-it
  [guesses]
  (let [string-length (count (ffirst guesses)),
        vars (repeatedly string-length lvar),
        strategically-ordered-guesses (sort-by second guesses),
        digits (partial map (comp read-string str))]
    (first (run 1 [s]
                (== s vars)
                (everyg #(infd % digits-domain) vars)
                (everyg (fn [[guess correct]]
                          (guesso (digits guess)
                                  correct
                                  vars))
                        strategically-ordered-guesses)))))

(comment
  ;; multiple solutions (still just returns the first)
  (solve-it [["90342" 2]
             ["70794" 0]])

  ;; unique solution (example from the problem description)
  (solve-it [["90342" 2]
             ["70794" 0]
             ["39458" 2]
             ["34109" 1]
             ["51545" 2]
             ["12531" 1]])

  ;; actual problem (never finishes? memory leaks?)
  (time (solve-it [["5616185650518293" 2]
                   ["3847439647293047" 1]
                   ["5855462940810587" 3]
                   ["9742855507068353" 3]
                   ["4296849643607543" 3]
                   ["3174248439465858" 1]
                   ["4513559094146117" 2]
                   ["7890971548908067" 3]
                   ["8157356344118483" 1]
                   ["2615250744386899" 2]
                   ["8690095851526254" 3]
                   ["6375711915077050" 1]
                   ["6913859173121360" 1]
                   ["6442889055042768" 2]
                   ["2321386104303845" 0]
                   ["2326509471271448" 2]
                   ["5251583379644322" 2]
                   ["1748270476758276" 3]
                   ["4895722652190306" 1]
                   ["3041631117224635" 3]
                   ["1841236454324589" 3]
                   ["2659862637316867" 2]])))