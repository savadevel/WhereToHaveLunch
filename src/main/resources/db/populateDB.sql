DELETE FROM users;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM menus;
DELETE FROM votes;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (username, password, role)
VALUES ('user1', '{noop}password', 'USER'),
       ('user2', '{noop}password', 'USER'),
       ('user3', '{noop}password', 'USER'),
       ('user4', '{noop}password', 'USER'),
       ('admin', '{noop}admin', 'ADMIN');

INSERT INTO dishes (name)
VALUES ('first dish'),                              -- 100_000
       ('second dish');                             -- 100_001

INSERT INTO restaurants (name)
VALUES ('first restaurant'),                        -- 100_002
       ('second restaurant');                       -- 100_003

INSERT INTO menus (restaurant_id, dish_id, on_date, price)
VALUES (100002, 100000, '2021-01-01', 0.53),      -- 100_004
       (100002, 100001, '2021-01-01', 5.03),      -- 100_005
       (100002, 100001, '2021-01-02', 5.03),      -- 100_006
       (100003, 100000, '2021-01-01', 0.53),      -- 100_007
       (100003, 100001, '2021-01-01', 5.03),      -- 100_008
       (100003, 100000, '2021-01-03', 0.53),      -- 100_009
       (100003, 100001, '2021-01-03', 5.03);      -- 100_010

INSERT INTO votes(on_date, restaurant_id, username)
values ('2021-01-01', 100002, 'user1'),            -- 100_011
       ('2021-01-02', 100002, 'user2'),            -- 100_012
       ('2021-01-02', 100002, 'user3'),            -- 100_013
       ('2021-01-01', 100003, 'user2'),            -- 100_014
       ('2021-01-01', 100003, 'user3'),            -- 100_015
       ('2021-01-02', 100003, 'user1'),            -- 100_016
       ('2021-01-03', 100003, 'user1'),            -- 100_017
       ('2021-01-03', 100003, 'user2'),            -- 100_018
       ('2021-01-03', 100003, 'user3')             -- 100_019