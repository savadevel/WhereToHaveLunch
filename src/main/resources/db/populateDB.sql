DELETE FROM users;
DELETE FROM user_roles;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM menus;
DELETE FROM votes;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, password)
VALUES ('user1', '{noop}password'), -- 100_000
       ('user2', '{noop}password'), -- 100_001
       ('user3', '{noop}password'), -- 100_002
       ('user4', '{noop}password'), -- 100_003
       ('admin', '{noop}admin'); -- 100_004

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('USER', 100001),
       ('USER', 100002),
       ('USER', 100003),
       ('ADMIN', 100004);

INSERT INTO dishes (name)
VALUES ('first dish'), -- 100_005
       ('second dish'); -- 100_006

INSERT INTO restaurants (name)
VALUES ('first restaurant'), -- 100_007
       ('second restaurant'); -- 100_008

INSERT INTO menus (restaurant_id, dish_id, on_date, price)
VALUES (100007, 100005, '2021-01-01', 0.53), -- 100_009
       (100007, 100006, '2021-01-01', 5.03), -- 100_010
       (100007, 100006, '2021-01-02', 5.03), -- 100_011
       (100008, 100005, '2021-01-01', 0.53), -- 100_012
       (100008, 100006, '2021-01-01', 5.03), -- 100_013
       (100008, 100005, '2021-01-03', 0.53), -- 100_014
       (100008, 100006, '2021-01-03', 5.03); -- 100_015

INSERT INTO votes(on_date, restaurant_id, user_id)
values ('2021-01-01', 100007, 100000), -- 100_016
       ('2021-01-02', 100007, 100001), -- 100_017
       ('2021-01-02', 100007, 100002), -- 100_018
       ('2021-01-01', 100008, 100001), -- 100_019
       ('2021-01-01', 100008, 100002), -- 100_020
       ('2021-01-02', 100008, 100000), -- 100_021
       ('2021-01-03', 100008, 100000), -- 100_022
       ('2021-01-03', 100008, 100001), -- 100_023
       ('2021-01-03', 100008, 100002)  -- 100_024