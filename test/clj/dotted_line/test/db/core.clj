(ns dotted-line.test.db.core
  (:require [dotted-line.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [dotted-line.config :refer [env]]
            [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
     #'dotted-line.config/env
     #'dotted-line.db.core/*db*)
    (migrations/migrate ["migrate"] (env :database-url))
    (f)))

;; (deftest test-customer
;;   (jdbc/with-db-transaction [t-conn *db*]
;;     (jdbc/db-set-rollback-only! t-conn)
;;     (let [customer {:first-name "test"
;;                     :last-name "test"
;;                     :email "test@test.com"}]
;;       (is (= 1 (db/save-customer! t-conn customer)))
;;       (let [result (db/get-customers t-conn {})]
;;         (is (= 1 (count result)))
;;         (is (= customer (dissoc (first result) :id))))))
;;   (is (empty? (db/get-customers))))

(deftest search-customer
  (jdbc/with-db-transaction [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (let [customer {:first_name "Manuel"
                    :last_name "Uberti"
                    :email "manuel@boccaperta.com"}]
      (is (seq? (db/get-customers t-conn customer))))))

;; (deftest search-enrolment
;;   (jdbc/with-db-transaction [t-conn *db*]
;;     (jdbc/db-set-rollback-only! t-conn)
;;     (let [customer {:id "1"}]
;;       (is (seq? (db/get-enrolments t-conn customer))))))
