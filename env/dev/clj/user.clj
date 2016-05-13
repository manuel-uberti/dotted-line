(ns user
  (:require [mount.core :as mount]
            dotted-line.core))

(defn start []
  (mount/start-without #'dotted-line.core/repl-server))

(defn stop []
  (mount/stop-except #'dotted-line.core/repl-server))

(defn restart []
  (stop)
  (start))


