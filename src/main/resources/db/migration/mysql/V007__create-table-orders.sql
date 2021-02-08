CREATE TABLE IF NOT EXISTS orders (
	id						BIGINT NOT NULL AUTO_INCREMENT,
	pasta_id				BIGINT NOT NULL,
	flavor_id				BIGINT NOT NULL,
	total					DECIMAL(8,2) NOT NULL,
	CONSTRAINT pk_orders_id PRIMARY KEY (id),
	CONSTRAINT fk_orders_pasta_id FOREIGN KEY (pasta_id) REFERENCES pastas (id),
	CONSTRAINT fk_orders_flavor_id FOREIGN KEY (flavor_id) REFERENCES flavors (id)
);