(ns dotted-line.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [dotted-line.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[dotted-line started successfully using the development profile]=-"))
   :middleware wrap-dev})
