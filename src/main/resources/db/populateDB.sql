DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE meal_seq START 503;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, meal_id, registered, description, calories)
VALUES (100001,1,'2004-10-19 10:23:54', 'Завтрак', 500);
;
INSERT INTO meals (user_id, meal_id, registered, description, calories)
VALUES (100001,2,'2013-5-19 11:00:00', 'Второй завтрак', 600);
;