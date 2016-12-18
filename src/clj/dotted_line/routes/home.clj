(ns dotted-line.routes.home
  (:require [dotted-line.db.core :as db]
            [bouncer.core :as b]
            [bouncer.validators :as v]
            [clj-time.format :as f]
            [dotted-line.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as hresponse]
            [ring.util.response :refer [response redirect content-type]]
            [clojure.java.io :as io]
            [clojure.string :as s]
            [clojure.spec :as spec]))

;;; Validations
(def custom-formatter (f/formatter "ddMMyyyy"))

(v/defvalidator valid-date
  {:default-message-format "%s must be dd/mm/yyyy"}
  [p]
  (when (and (s/includes? p "/")
             (= 10 (count p)))
    (let [date (.replaceAll p "/" "")]
      (f/parse custom-formatter date))))

(defn validate-customer [params]
  (first
   (b/validate
    params
    :first_name v/required
    :last_name v/required
    :email v/email
    :date_of_birth valid-date)))

(defn validate-selection [params]
  (first
   (b/validate
    params
    :id_customer v/required)))

(defn validate-enrolment [params]
  (first
   (b/validate
    params
    :date_in valid-date
    :balance v/required
    :course v/required)))

;;; Queries
(defn show-customers []
  (layout/render
   "show.html"
   {:customers (db/get-customers)}))

(defn search-customer [{:keys [params]} pageok pageko]
  (let [lname (str "%" (:last_name params) "%")
        lparams {:last_name lname}
        customers (db/search-customer-by-lname lparams)]
    (if (= (count customers) 1)
      (layout/render
       pageok
       {:customers customers})
      (layout/render
       pageko
       {:customers customers}))))

(defn insert-customer! [{:keys [params]}]
  (if-let [errors (validate-customer params)]
    (-> (hresponse/found "/insert")
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/insert-customer! params)
      (hresponse/found "/show"))))

(defn update-customer! [{:keys [params]}]
  (if-let [errors (validate-customer params)]
    (layout/render
     "edit.html"
     (assoc :flash (assoc params :errors errors)))
    (do
      (db/update-customer! params)
      (layout/render
       "edit.html"
       {:customers (db/search-customer-by-id params)}))))

(defn delete-customer! [{:keys [params]}]
  (db/delete-customer! params)
  (hresponse/found "/show"))

(defn update-enrolment! [{:keys [params]}]
  (if-let [errors (validate-enrolment params)]
    (layout/render
     "editenrol.html"
     (assoc :flash (assoc params :errors errors)))
    (do
      (db/update-enrolment! params)
      (layout/render
       "editenrol.html"
       {:enrolments (db/get-enrolments)
        :courses (db/get-courses)}))))

(defn insert-enrolment! [{:keys [params]}]
  (if-let [errors (validate-enrolment params)]
    (-> (hresponse/found "/insertenrol")
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/insert-enrolment! params)
      (hresponse/found "/searchcusenr"))))

;;; Pages
(defn home-page []
  (layout/render "home.html"))

(defn about-page []
  (layout/render "about.html"))

(defn search-page [{:keys [flash]}]
  (layout/render
   "search.html"
   (select-keys flash [:last_name :errors])))

(defn edit-page [{:keys [params]}]
  (layout/render
   "edit.html"
   {:customers (db/search-customer-by-id {:id (:id_customer params)})}))

(defn delete-page [{:keys [params]}]
  (layout/render
   "delete.html"
   {:customers (db/search-customer-by-id {:id (:id_customer params)})}))

(defn insert-page [{:keys [flash]}]
  (layout/render
   "insert.html"
   (select-keys flash [:first_name :last_name
                       :email :date_of_birth
                       :errors])))

(defn searchenrol-page [{:keys [flash]}]
  (layout/render
   "searchenrol.html"
   (select-keys flash [:errors])))

(defn enrol-page [{:keys [params]}]
  (layout/render
   "editenrol.html"
   {:enrolments (db/get-enrolments {:id (:id_customer params)})
    :courses (db/get-courses)}))

(defn searchcusenr-page [{:keys [flash]}]
  (layout/render
   "searchcusenr.html"
   (select-keys flash [:errors])))

(defn insertenrol-page [{:keys [params]}]
  (layout/render
   "insertenrol.html"
   {:customers (db/search-customer-by-id {:id (:id_customer params)})
    :courses (db/get-courses)}))

;;; Routes
(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/show" [] (show-customers))
  (GET "/search" request (search-page request))
  (POST "/dosearch" request
        (search-customer request "edit.html" "search.html"))
  (POST "/edit" request (edit-page request))
  (POST "/update" request (update-customer! request))
  (POST "/delete" request (delete-page request))
  (POST "/dodelete" request (delete-customer! request))
  (GET "/insert" request (insert-page request))
  (POST "/save" request (insert-customer! request))
  (GET "/searchenrol" request (searchenrol-page request))
  (POST "/dosearchenrol" request
        (search-customer request "doenrol.html" "searchenrol.html"))
  (POST "/viewenrol" request (enrol-page request))
  (POST "/saveenrol" request (update-enrolment! request))
  (GET "/searchcusenr" request (searchcusenr-page request))
  (POST "/dosearchcusenr" request
        (search-customer request "insertenrol.html" "searchcusenr.html"))
  (POST "/insertenrol" request (insertenrol-page request))
  (POST "/doinsertenr" request (insert-enrolment! request)))
