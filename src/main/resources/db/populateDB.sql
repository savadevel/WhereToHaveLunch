DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM menus;
DELETE FROM votes;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, password)
VALUES ('user', 'password'),                    -- 100_000
       ('admin', 'admin');                      -- 100_001

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),                       --
       ('ADMIN', 100001);                      --

INSERT INTO dishes (name, price)
VALUES ('first dish', 0.53),                    -- 100_002
       ('second dish', 5.03);                   -- 100_003

INSERT INTO restaurants (name)
VALUES ('first restaurant'),                    -- 100_004
       ('second restaurant');                   -- 100_005

INSERT INTO menus (restaurant_id, dish_id, on_date)
VALUES (100004, 100002, '2021-01-01'),          -- 100_006
       (100004, 100003, '2021-01-01'),          -- 100_007
       (100004, 100003, '2021-01-02'),          -- 100_008
       (100005, 100002, '2021-01-01'),          -- 100_009
       (100005, 100003, '2021-01-01'),          -- 100_010
       (100005, 100002, '2021-01-03'),          -- 100_011
       (100005, 100003, '2021-01-03');          -- 100_012
