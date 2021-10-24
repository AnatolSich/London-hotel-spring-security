DROP TABLE IF EXISTS t_user_roles;
DROP TABLE IF EXISTS t_user;
DROP TABLE IF EXISTS t_role;

CREATE TABLE public.t_role
(
    id         INT  PRIMARY KEY,
    name VARCHAR(250) NOT NULL
);

CREATE TABLE public.t_user
(
    id   INT  PRIMARY KEY,
    username VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL
);

CREATE TABLE public.t_user_roles
(
    id      INT  PRIMARY KEY,
    user_id    int          not null,
    roles_id int          not null
--     FOREIGN KEY (user_id) REFERENCES t_user (id) ON DELETE CASCADE,
--     FOREIGN KEY (roles_id) REFERENCES t_role (id) ON DELETE CASCADE
);

--'password'
INSERT INTO public.t_user(id, username, password)
VALUES (1, 'user', '$2a$11$3NO32OV1TGjap3xMpAEjmuiizitWuaSwUYz42aMtlxRliwJ8zm4Sm'), (2, 'fpmoles', '$2a$11$dp4wMyuqYE3KSwIyQmWZFeUb7jCsHAdk7ZhFc0qGw6i5J124imQBi');

INSERT INTO public.t_role(id, name)
VALUES (1, 'ROLE_ADMIN_USER'), (2, 'ROLE_REGISTERED_USER');

INSERT INTO public.t_user_roles(id, user_id, roles_id)
VALUES (1, 1, 2), (2, 2, 1);

