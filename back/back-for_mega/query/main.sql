CREATE TABLE user_table
(
    id               SERIAL primary key,
    user_name        VARCHAR(50),
    first_name       VARCHAR(25),
    second_name      VARCHAR(25),
    middle_name      VARCHAR(25),
    age              int4,
    address          VARCHAR(35),
    pass             VARCHAR(50),
    email            varchar(50),
    phone            VARCHAR(20),
    birthday         DATE,
    register_account DATE
);

CREATE TABLE card_table
(
    id        SERIAL PRIMARY KEY,
    id_user   INT8,
    number    VARCHAR(16),
    data_name VARCHAR(5),
    svv       VARCHAR(3),
    FOREIGN KEY (id_user) REFERENCES user_table (id)
);

CREATE TABLE history_table
(
    id           SERIAL,
    PRIMARY KEY (id),
    id_user      INT8,
    block_wallet VARCHAR(255),
    dates   TIMESTAMP,
    name_crypt   VARCHAR(255),
    counts       DECIMAL,
    FOREIGN KEY (id_user) REFERENCES user_table (id)
);
CREATE TABLE crypto_table
(
    id         SERIAL PRIMARY KEY,
    price      DECIMAL,
    price_sell DECIMAL,
    change     DECIMAL,
    dates      TIMESTAMP,
    name       VARCHAR(255),
    full_name  VARCHAR(255)
);

CREATE TABLE wallet
(
    id          SERIAL PRIMARY KEY,
    id_user     int4,
    name_wallet VARCHAR(64),
    name_crypt  VARCHAR(20),
    count       DECIMAL,
    FOREIGN KEY (id_user) REFERENCES user_table (id)
)