(ns dec-18-2012.zebra
  (:refer-clojure :exclude [==])
  (:require [clojure.tools.macro :as macro])
  (:use clojure.core.logic))

(defne righto [x y l]
  ([_ _ [x y . ?r]])
  ([_ _ [_ . ?r]] (righto x y ?r)))

(defn nexto [x y l]
  (conde
    [(righto x y l)]
    [(righto y x l)]))

(defn run-zebra
  []
  (macro/symbol-macrolet [_ (lvar)]
                         (run 1 [houses]
                              (== houses [_ _ _ _ _])
                              (membero [:englishman _ _ _ :red] houses)
                              (membero [:spaniard _ _ :dog _] houses)
                              (membero [_ _ :coffee _ :green] houses)
                              (membero [:ukrainian _ :tea _ _] houses)
                              (righto [_ _ _ _ :ivory] [_ _ _ _ :green] houses)
                              (membero [_ :oldgolds _ :snails _] houses)
                              (membero [_ :kools _ _ :yellow] houses)
                              (== [_ _ [_ _ :milk _ _] _ _] houses)
                              (firsto houses [:norwegian _ _ _ _])
                              (nexto [_ _ _ :fox _] [_ :chesterfields _ _ _] houses)
                              (nexto [_ _ _ :horse _] [_ :kools _ _ _] houses)
                              (membero [_ :lucky-strikes :oj _ _] houses)
                              (membero [:japanese :parliaments _ _ _] houses)
                              (nexto [:norwegian _ _ _ _] [_ _ _ _ :blue] houses))))
