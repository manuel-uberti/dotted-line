(ns dotted-line.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [dotted-line.layout :refer [error-page]]
            [dotted-line.routes.home :refer [home-routes]]
            [compojure.route :as route]
            [dotted-line.middleware :as middleware]))

(def app-routes
  (routes
   ;; TODO: enable login (add auth-routes in :require)
   ;; (wrap-routes #'auth-routes middleware/wrap-csrf)
   ;; (-> home-routes
   ;;     (wrap-routes middleware/wrap-csrf)
   ;;     (wrap-routes middleware/wrap-restricted))
   (wrap-routes #'home-routes middleware/wrap-csrf)
   (route/not-found
    (:body
     (error-page {:status 404
                  :title "page not found"})))))

(defn app [] (middleware/wrap-base #'app-routes))
