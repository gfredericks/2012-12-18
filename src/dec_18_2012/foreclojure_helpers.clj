(ns dec-18-2012.foreclojure-helpers
  (:require [misquote.core :refer [misquote]]
            [clojure.walk :as wk]))

(defmacro def-4c-problem
  "You did not just see me write this macro-writing macro."
  [name doc form]
  (list 'defmacro
        name
        doc
        '[& args]
        (list `misquote
              (wk/postwalk
               (fn [x]
                 (if (= x '__)
                   '(clojure.core/unquote-splicing args)
                   x))
               form))))