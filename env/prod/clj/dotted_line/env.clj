(ns dotted-line.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[dotted-line started successfully]=-"))
   :middleware identity})
