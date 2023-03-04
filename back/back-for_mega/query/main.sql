CREATE TABLE user_table (
  id SERIAL,
  first_name VARCHAR(25),
  second_name VARCHAR(25),
  middle_name VARCHAR(25),
  age INT,
  address  VARCHAR(35),
  phone VARCHAR(20),
  card_id INT,
  history_id INT,
  crypto_id INT,
  FOREIGN KEY (card_id) REFERENCES card_table (id),
  FOREIGN KEY (history_id) REFERENCES history_table (id),
  FOREIGN KEY (crypto_id) REFERENCES  crypto_table (id)
);

CREATE TABLE card_table (
  id SERIAL,
  number VARCHAR(16),
  data_name VARCHAR(5),
  svv VARCHAR(3)
);

CREATE TABLE history_table (
  id SERIAL,
  PRIMARY KEY (id),
  block_wallet VARCHAR(255),
  price_date_buy DATE,
  price_date_sell DATE,
  price_buy DECIMAL,
  price_sell DECIMAL
);
CREATE TABLE crypto_table (
  id SERIAL,
  PRIMARY KEY (id),
  price DECIMAL,
  price_sell DECIMAL,
  name VARCHAR(255)
);