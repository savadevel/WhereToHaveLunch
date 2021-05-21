DROP TABLE votes IF EXISTS;
DROP TABLE user_roles IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE menus IF EXISTS;
DROP TABLE dishes IF EXISTS;
DROP TABLE restaurants IF EXISTS;
DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE users
(
    id       INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name     VARCHAR(128) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT users_unique_name_idx UNIQUE (name)
);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_unique_username_role_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE dishes
(
    id   INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT dishes_unique_name_idx UNIQUE (name)
);

CREATE TABLE restaurants
(
    id   INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT restaurants_unique_name_idx UNIQUE (name)
);

CREATE TABLE menus
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    restaurant_id INTEGER        NOT NULL,
    dish_id       INTEGER        NOT NULL,
    on_date       DATE           NOT NULL,
    price         DECIMAL(20, 2) NOT NULL CHECK (price >= 0.01 and price <= 10000.00),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE,
    FOREIGN KEY (dish_id) REFERENCES dishes (id) ON DELETE CASCADE,
    CONSTRAINT menus_unique_restaurant_dish_on_date_idx UNIQUE (restaurant_id, dish_id, on_date)
);

CREATE TABLE votes
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    on_date       DATE    NOT NULL,
    restaurant_id INTEGER NOT NULL,
    user_id       INTEGER NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT votes_unique_restaurant_user_on_date_idx UNIQUE (user_id, on_date)
);

CREATE INDEX menus_on_date_idx ON menus (on_date);

CREATE INDEX votes_on_date_idx ON menus (on_date);