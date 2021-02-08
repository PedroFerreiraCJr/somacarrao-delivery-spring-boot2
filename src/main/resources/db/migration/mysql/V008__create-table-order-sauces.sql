CREATE TABLE IF NOT EXISTS order_sauces (
	order_id				BIGINT NOT NULL,
	sauce_id				BIGINT NOT NULL,
	CONSTRAINT pk_order_sauces PRIMARY KEY (order_id, sauce_id),
	CONSTRAINT fk_order_sauces_order_id FOREIGN KEY (order_id) REFERENCES orders (id),
	CONSTRAINT fk_order_sauces_sauce_id FOREIGN KEY (sauce_id) REFERENCES sauces (id)
);