CREATE TABLE enrolments
(
id SERIAL PRIMARY KEY,
id_customer INTEGER REFERENCES customers(ID),
date_in DATE,
balance NUMERIC(7,2),
date_deposit DATE,
deposit NUMERIC(7,2),
instalment_1 NUMERIC(7,2),
date_instalment_1 DATE,
instalment_2 NUMERIC(7,2),
date_instalment_2 DATE,
instalment_3 NUMERIC(7,2),
date_instalment_3 DATE,
id_courses INTEGER REFERENCES courses(id),
module_1 BOOLEAN,
module_2 BOOLEAN,
module_3 BOOLEAN
);
