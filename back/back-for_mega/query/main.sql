CREATE TABLE user_table (
  id SERIAL primary key ,
  first_name VARCHAR(25),
  second_name VARCHAR(25),
  middle_name VARCHAR(25),
  age INT,
  address  VARCHAR(35),
  pass VARCHAR(50),
  email varchar(50),
  phone VARCHAR(20)
);

CREATE TABLE card_table (
  id SERIAL PRIMARY KEY ,
  id_user INT8,
  number VARCHAR(16),
  data_name VARCHAR(5),
  svv VARCHAR(3),
  FOREIGN KEY (id_user) REFERENCES user_table (id)
);

CREATE TABLE history_table (
  id SERIAL,
  PRIMARY KEY (id),
  id_user INT8,
  block_wallet VARCHAR(255),
  price_date_buy DATE,
  price_date_sell DATE,
  price_buy DECIMAL,
  price_sell DECIMAL,
  FOREIGN KEY (id_user) REFERENCES user_table (id)
);
CREATE TABLE crypto_table (
  id SERIAL PRIMARY KEY,
  price DECIMAL,
  price_sell DECIMAL,
  change DECIMAL,
  dates TIMESTAMP,
  name VARCHAR(255)
);