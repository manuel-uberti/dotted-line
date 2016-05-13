CREATE TABLE customers
(
id SERIAL PRIMARY KEY,
first_name VARCHAR(30) NOT NULL,
last_name VARCHAR(30) NOT NULL,
date_of_birth DATE,
place_of_birth VARCHAR(30),
fiscal_code VARCHAR(16),
vat_number VARCHAR(16),
address VARCHAR(50),
city VARCHAR(50),
postal_code VARCHAR(5),
province VARCHAR(50),
email VARCHAR(50),
phone VARCHAR(15),
notes VARCHAR(1000),
active BOOLEAN DEFAULT TRUE
);
