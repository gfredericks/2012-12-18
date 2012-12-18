(ns dec-18-2012.foreclojure
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]
            [dec-18-2012.foreclojure-helpers :refer [def-4c-problem]]))


;;;
;;; Failure
;;;

(def-4c-problem p-1
  "The fail goal never succeeds. run* always returns
  a (possibly empty) list of values for q."
  (= __ (run* [q] fail)))


;;;
;;; Success
;;;

(def-4c-problem p-2
  "The succeed goal always succeeds. "
  (= __ (run* [q] succeed)))


;;;
;;; Unification 1
;;;

(def-4c-problem p-3
  "Unification succeeds when the two terms can be made equal."
  (= __ (run* [q] (== :mark q))))


;;;
;;; Unification 2
;;;

(def-4c-problem p-4
  "Unification succeeds when the two terms can be made equal."
  (= __ (run* [q] (== 77 15))))


;;;
;;; Unification 3
;;;

(def-4c-problem p-5
  "Unification succeeds when the two terms can be made equal."
  (= __ (run* [q] (== q 97) (== q 98))))


;;;
;;; Unification 4
;;;

(def-4c-problem p-6
  ""
  (= __ (run* [q] succeed (== true q))))


;;;
;;; Unification 5
;;;

(def-4c-problem p-7
  ""
  (= __ (run* [r] succeed (== :corn r))))


;;;
;;; Lettings 1
;;;

(def-4c-problem p-8
  "You can (somewhat) mix logic programming with functional programming."
  (= __ (run* [q] (let [x true] (== false x)))))


;;;
;;; Lettings 2
;;;

(def-4c-problem p-9
  "Let works the way you (hopefully) would expect."
  (= __ (run* [q] (let [x false] (== false x)))))


;;;
;;; Freshings 1
;;;

(def-4c-problem p-10
  "Fresh introduces new logic variables."
  (= __ (run* [q] (fresh [x] (== true x) (== true q)))))


;;;
;;; Freshings 2
;;;

(def-4c-problem p-11
  "What happens if we unify two logic variables?"
  (= __ (run* [q] (fresh [x] (== x q) (== :snazzy x)))))


;;;
;;; Scopings
;;;

(def-4c-problem p-12
  "Oh man which x is which? What does it all mean??"
  (= __ (run* [x] (let [x true] (fresh [x] (== false x))))))


;;;
;;; List-unification
;;;

(def-4c-problem p-13
  "Core.logic supports unifying sequential
  things. Vectors and seqs are treated the same."
  (= __ (run* [q] (fresh [x y] (== [x y] q)))))


;;;
;;; Multiple Query Parameters
;;;

(def-4c-problem p-14
  "When we use multiple query parameters, run returns a
                list of tuples instead of a list of single values."
  (and
       (= __ (run* [a b c] (== a c) (== c :foo) (== b 42)))
       (= __ (run* [q] (fresh [a b c] (== q [a b c]) (== a c) (== c :foo) (== b 42))))))


;;;
;;; Oh man what is this doing
;;;

(def-4c-problem p-15
  "It's all nonsense! Who would write code like this?"
  (= __ (run* [r] (fresh [x] (let [y x] (fresh [x] (== [y x y] r)))))))


;;;
;;; Unify twice! Unify twice!
;;;

(def-4c-problem p-16
  "What happens if we try to unify a logic variable to
  two different values?"
  (= __ (run* [q] (== false q) (== true q))))


;;;
;;; Unify twice again! Unify twice again!
;;;

(def-4c-problem p-17
  "What happens if we try to unify a logic variable to
  the same value twice?"
  (= __ (run* [q] (== false q) (== false q))))


;;;
;;; Run! Let!
;;;

(def-4c-problem p-18
  "Can a regular clojure local refer to a logic variable?"
  (= __ (run* [q] (let [x q] (== true x)))))


;;;
;;; Run! Fresh!
;;;

(def-4c-problem p-19
  "What happens if we unify two logic variables when
  neither is ground?"
  (= __ (run* [q] (fresh [x] (== x q)))))


;;;
;;; Run! Fresh! Two!
;;;

(def-4c-problem p-20
  "What if we unify q with x and then give x a value \"later\"?"
  (= __ (run* [q] (fresh [x] (== x q) (== true x)))))


;;;
;;; Conde 1
;;;

(def-4c-problem p-21
  "Conde lets us give independent alternatives. The top
  level clauses (in square brackets) can succeed or fail
  indepedently. Conde is the primary mechanism by which a logic
  program can return multiple values. The clauses inside the square
  brackets must evaluate to goals, and are combined with conjunction
  (i.e., \"and\")."
  (= __ (run* [q]
               (conde
                 [(== q :foo)]
                 [(== :bar q)]))))


;;;
;;; Conde 2
;;;

(def-4c-problem p-22
  "Yep."
  (= __ (run* [a b c]
               (conde
                 [(== a :foo) (== b :bar)]
                 [(== :tambourine b) (== c "stingray")]))))


;;;
;;; Conde 3
;;;

(def-4c-problem p-23
  "What happens if one of the clauses contains a
  contradiction?"
  (= __ (run* [q]
               (conde
                [(== q :foo)]
                [(== :bar q) (== q :baz)]))))


;;;
;;; Conde 4
;;;

(def-4c-problem p-24
  "This description is unhelpful."
  (= __ (run* [q]
               (conde
                 [(== q :foo) (== :baz :blizzles)]
                 [(== :tim :tim) (== :bar q)]))))


;;;
;;; Conde 5
;;;

(def-4c-problem p-25
  "What's going on here? How many clauses is that? What
  the I don't even"
  (= __ (run* [q] (conde [fail succeed]))))


;;;
;;; Conde 6
;;;

(def-4c-problem p-26
  "Fill in the conde clauses such that the program
  evalues to [:tim :buck :two]."
  (= [:tim :buck :two] (run* [q] (conde __))))


;;;
;;; Run 1
;;;

(def-4c-problem p-27
  "When you use run instead of run* you can limit the
  results to a particular number."
  (= __ (run 1 [q]
               (conde
                 [(== q :foo)]
                 [(== :bar q)]))))


;;;
;;; Conde 7
;;;

(def-4c-problem p-28
  "For advanced beginners only."
  (= __ (run* [q]
               (conde
                 [(== :virgin q) fail]
                 [(== :olive q) succeed]
                 [succeed succeed]
                 [(== :oil q) succeed]
                 [succeed fail]))))


;;;
;;; Conde 8
;;;

(def-4c-problem p-29
  ""
  (= __ (run* [r]
               (fresh [x y]
                 (conde
                   [(== :split x) (== :pea y)]
                   [(== :navy x) (== :bean y)])
                 (== [x y] r)))))


;;;
;;; Oh man that's trippy.
;;;

(def-4c-problem p-30
  "Wait we're giving local names to several different
  goals? Why would we even do that? Is this supposed to illustrate
  something about how the code is evaluated? IS THIS ALL JUST FOR
  LEARNINGS??"
  (= __ (run* [q]
               (let [a (== true q),
                     b (fresh [x]
                         (== x q)
                         (== false x)),
                     c (conde
                         [(== true q) succeed]
                         [(== false q)])]
                 b))))


;;;
;;; Sequences 1
;;;

(def-4c-problem p-31
  "Firsto is a relation on sequences where the second
  argument is the first element of the first argument. It is the
  relational version of clojure.core/first."
  (= __ (run* [r]
               (firsto '(a c o r n) r))))


;;;
;;; Sequences 2
;;;

(def-4c-problem p-32
  "Oh yeah, well why don't YOU try writing the
  descriptions for all of these problems?"
  (= __ (run* [q]
               (firsto '(a c o r n) 'a)
               (== true q))))


;;;
;;; Sequences 3
;;;

(def-4c-problem p-33
  "What if the list has logic variables in it? CAN ALL THIS REALLY WORK??"
  (= __ (run* [r]
               (fresh [x y]
                 (firsto [r y] x)
                 (== :pear x)))))


;;;
;;; Sequences 4
;;;

(def-4c-problem p-34
  "Conso is the relational version of cons. (conso a b c) declares that c is
  a sequence whose first element is a and whose rest is b."
  (= __ (run* [r]
               (fresh [x y]
                 (firsto '(grape raisin pear) x)
                 (firsto '((a) (b) (c)) y)
                 (conso x y r)))))


;;;
;;; Sequences 5
;;;

(def-4c-problem p-35
  "Resto is the relational version of rest."
  (= __ (run* [r]
               (fresh [v]
                 (resto '(a c o r n) v)
                 (firsto v r)))))


;;;
;;; Sequences 6
;;;

(def-4c-problem p-36
  "Try to write a relation that succeeds when its input is a list. Note that strings are not considered sequences by the core.logic unifier."
  (and
       (= '(_0) (run 1 [q] (__ [7 8 9])))
       (= () (run 1 [q] (__ 42)))
       (= '([[:foo]]) (run 1 [q] (__ q) (== q [[:foo]])))
       (= '() (run 1 [q] (__ q) (== q "seventeen")))))


;;;
;;; Matche 1
;;;

(def-4c-problem p-37
  "Matche is a pattern-matching goal that can largely
  replace tedious uses of fresh, firsto, resto, etc. See also defne.
  The clauses are tested independently, like conde."
  (= __ (run* [q]
               (matche [:foo]
                 ([:foo] (== q 15))
                 ([:bar] (== q 42))))))


;;;
;;; Matche 2
;;;

(def-4c-problem p-38
  "This is another example with matche."
  (= __ (run* [q]
               (matche [q]
                 ([:achey])
                 ([:breaky] (== q :tamborine))
                 ([:heart] (== :heart q))))))


;;;
;;; Matche 3
;;;

(def-4c-problem p-39
  "Moar matche!"
  (= __ (run* [q]
               (matche [q]
                 ([[1 2 3 . more]] (== more [7 8 9]))
                 ([[a b c]] (== b "bee") (== c b))
                 ([[[]]] (== q 42))))))


;;;
;;; Matche 4
;;;

(def-4c-problem p-40
  "Pattern matching lets us implicitely unify two things
  by giving them the same name."
  (= __ (run* [a b c]
               (matche [a b]
                 ([17 [x]] (== x c))
                 ([x x])))))


;;;
;;; Branching
;;;

(def-4c-problem p-41
  "So many possibilities! (Note the return value is a set, so you
                don't have to worry about ordering)"
  (= __ (set
               (let [good-number (fn [x] (conde [(== 42 x)] [(== x 1024)])),
                    somewhat-nested (fn [x] (matche [x] ([[y]]) ([[[y]]])))]
                 (run* [a b]
                   (good-number a)
                   (somewhat-nested b))))))


;;;
;;; Writing relations 1
;;;

(def-4c-problem p-42
  "Write a relation that succeeds when its input is a list of doubly-repeated elements."
  (and
       (= ['_0] (letfn [(twinsies [x] __)] (run* [q] (twinsies [:a :a :b :b :c :c]) (twinsies []))))
       (= [] (letfn [(twinsies [x] __)] (run* [q] (twinsies [:a :a :b :b :c :c 42]))))
       (= [[:a :b]] (letfn [(twinsies [x] __)]
                      (run* [x y] (twinsies [7 7 :a x y :b]))))))


;;;
;;; Writing relations 2
;;;

(def-4c-problem p-43
  "Write a relation that succeeds when its input is a list of pairs of elements."
  (and
       (= ['_0] (letfn [(twinsies [x] __)] (run* [q] (twinsies [[:a :a] [:b :b] [:c :c]]) (twinsies []))))
       (= [] (letfn [(twinsies [x] __)] (run* [q] (twinsies [[:a :a] [:b :b] [:c :c] 42]))))
       (= [[:a :b]] (letfn [(twinsies [x] __)]
                      (run* [x y] (twinsies [[7 7] [:a x] [y :b]]))))))
