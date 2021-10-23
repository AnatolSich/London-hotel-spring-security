DROP TABLE IF EXISTS t_user_roles;
DROP TABLE IF EXISTS t_user;
DROP TABLE IF EXISTS t_role;

INSERT INTO public.t_user(id, username, password)
VALUES (1, 'user', 'password3'), (2, 'fpmoles', 'password2');

INSERT INTO public.t_role(id, name)
VALUES (1, 'ROLE_ADMIN_USER'), (2, 'ROLE_REGISTERED_USER');

INSERT INTO public.t_user_roles(user_id, roles_id)
VALUES (1, 2), (2, 1);