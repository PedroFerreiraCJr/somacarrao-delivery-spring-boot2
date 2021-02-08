CREATE TABLE IF NOT EXISTS ingredients (
	id						BIGINT NOT NULL AUTO_INCREMENT,
	name					VARCHAR(100) NOT NULL,
	description				VARCHAR(100) NOT NULL,
	price					DECIMAL(8,2) NOT NULL,
	CONSTRAINT pk_ingredients_id PRIMARY KEY (id)
);