CREATE TABLE IF NOT EXISTS roles (
	id					BIGINT NOT NULL AUTO_INCREMENT,
	name				VARCHAR(255) NOT NULL,
	CONSTRAINT pk_roles_id PRIMARY KEY (id)
);