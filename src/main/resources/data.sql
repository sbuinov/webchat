-- users
INSERT INTO users(id, email, enabled, password, username) 
	SELECT 1, 'user@email.com', TRUE, 'pass', 'user'
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE id=1);

INSERT INTO users(id, email, enabled, password, username) 
	SELECT 2, 'user1@email.com', TRUE, 'pass', 'user1'
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE id=2);

INSERT INTO users(id, email, enabled, password, username) 
	SELECT 3, 'user2@email.com', TRUE, 'pass', 'user2'
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE id=3);

INSERT INTO users(id, email, enabled, password, username) 
	SELECT 4, 'user3@email.com', TRUE, 'pass', 'user3'
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE id=4);

-- user_roles
INSERT INTO user_roles(user_role_id, role, userid)
	SELECT 1, 'USER', 1
    WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE user_role_id=1);

INSERT INTO public.user_roles(user_role_id, role, userid)
	SELECT 2, 'USER', 2
    WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE user_role_id=2);

INSERT INTO public.user_roles(user_role_id, role, userid)
	SELECT 3, 'USER', 3
    WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE user_role_id=3);

INSERT INTO public.user_roles(user_role_id, role, userid)
	SELECT 4, 'USER', 4
    WHERE NOT EXISTS (SELECT 1 FROM user_roles WHERE user_role_id=4);
