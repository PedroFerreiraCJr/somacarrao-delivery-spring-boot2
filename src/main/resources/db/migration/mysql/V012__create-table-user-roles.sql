CREATE TABLE IF NOT EXISTS user_roles (
	user_id					BIGINT NOT NULL,
	role_id					BIGINT NOT NULL,
	CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_id),
	CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_id) REFERENCES users (id),
	CONSTRAINT fk_user_roles_role_id FOREIGN KEY (role_id) REFERENCES roles (id)
);