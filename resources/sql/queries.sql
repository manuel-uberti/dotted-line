-- :name insert-user! :! :n
-- :doc create a new user account
INSERT INTO users
(first_name, last_name, username, passw)
VALUES
(:first_name, :last_name, :username, :passw)

-- :name get-user :? :*
-- :doc retrieve user by username and password
SELECT * FROM users
WHERE username = :username
AND passw = :passw

-- :name insert-customer! :! :n
-- :doc creates a new customer record
INSERT INTO customers
(first_name, last_name, date_of_birth, place_of_birth,
fiscal_code, vat_number, address, city, postal_code,
province, email, phone, notes)
VALUES
(:first_name, :last_name, to_date(:date_of_birth, 'DD/MM/YYYY'),
:place_of_birth, :fiscal_code, :vat_number, :address, :city,
:postal_code, :province, :email, :phone, :notes)

-- :name get-customers :? :*
-- :doc retrieve all customers
SELECT first_name, last_name, date_of_birth, place_of_birth,
fiscal_code, vat_number, address, city, postal_code,
province, email, phone, notes
FROM customers
ORDER BY last_name

-- :name delete-customer! :! :n
-- :doc delete a customer given the id
DELETE FROM customers
WHERE id = :id

-- :name search-customer-by-lname :? :*
-- :doc retrieve customers by last name
SELECT * FROM customers
WHERE UPPER(last_name) like UPPER(:last_name)
ORDER BY last_name

-- :name search-customer-by-id :? :*
-- :doc retrieve customers by last name
SELECT * FROM customers
WHERE id = TO_NUMBER(:id, '9999')

-- :name update-customer! :! :n
-- :doc update a customer
UPDATE customers SET
first_name = :first_name,
last_name = :last_name,
date_of_birth = to_date(:date_of_birth, 'DD/MM/YYYY'),
place_of_birth = :place_of_birth,
fiscal_code = :fiscal_code,
vat_number = :vat_number,
address = :address,
city = :city,
postal_code = :postal_code,
province = :province,
email = :email,
phone = :phone,
notes = :notes
WHERE id = TO_NUMBER(:id, '9999')

-- :name delete-customer! :! :n
-- :doc delete a customer by id
DELETE FROM customers
WHERE id = TO_NUMBER(:id, '9999')

-- :name get-courses :? :*
-- :doc retrive all courses
SELECT * FROM courses
ORDER BY title

-- :name get-enrolments :? :*
-- :doc retrieve enrolments for given customer
SELECT e.id, e.id_customer, e.date_in,
e.deposit, e.balance,
e.instalment_1, e.date_instalment_1,
e.instalment_2, e.date_instalment_2,
e.instalment_3, e.date_instalment_3,
e.id_courses, e.module_1, e.module_2, e.module_3,
c.first_name, c.last_name, cou.title
FROM enrolments e
INNER JOIN customers c
ON e.id_customer=c.ID
INNER JOIN courses cou
ON cou.id=e.id_courses
WHERE c.id = TO_NUMBER(:id, '9999')

-- :name update-enrolment! :! :n
-- :doc update an enrolment
UPDATE enrolments SET
id_customer = :id_customer,
date_in = :date_in,
balance = :balance,
deposit = :deposit,
date_deposit = :date_deposit,
instalment_1 = :instalment_1,
date_instalment_1 = :date_instalment_1,
instalment_2 = :instalment_2,
date_instalment_2 = :date_instalment_2,
instalment_3 = :instalment_3,
date_instalment_3 = :date_instalment_3,
id_courses = :id_courses,
module_1 = :module_1,
module_2 = :module_2,
module_3 = :module_3
WHERE id = TO_NUMBER(:id, '9999')

-- :name insert-enrolment! :! :n
-- :doc insert an enrolment
INSERT INTO enrolments SET
(id_customer, date_in, balance, deposit, date_deposit,
instalment_1, date_instalment_1, instalment_2, date_instalment_2,
instalment_3, date_instalment_3, id_courses, module_1,
module_2, module_3)
VALUES
(:id_customer, :date_in, :balance, :deposit, :date_deposit,
:instalment_1, :date_instalment_1, :instalment_2, :date_instalment_2,
:instalment_3, :date_instalment_3, :id_courses, :module_1,
:module_2, :module_3)
