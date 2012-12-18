(ns dec-18-2012.polynomials
  (:refer-clojure :exclude [==])
  (:use clojure.core.logic))

(defn poly-mult
  "p1 * p2 == p3. p1 and p2 are binomials, p3 trinomial."
  [p1 p2 p3]
  (fresh [a b c d e f g tmp1 tmp2]
         (infd a b c d e f g tmp1 tmp2 (interval 1 100))
         (== p1 [a b])   ; a + bx
         (== p2 [c d])   ; c + dx
         (== p3 [e f g]) ; e + fx + gx^2
         (*fd a c e)
         (*fd b d g)
         (*fd a d tmp1)
         (*fd b c tmp2)
         (+fd tmp1 tmp2 f)))
