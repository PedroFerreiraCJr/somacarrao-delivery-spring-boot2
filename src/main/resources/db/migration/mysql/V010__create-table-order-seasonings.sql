CREATE TABLE IF NOT EXISTS order_seasonings (
	order_id				BIGINT NOT NULL,
	seasoning_id			BIGINT NOT NULL,
	CONSTRAINT pk_order_seasonings PRIMARY KEY (order_id, seasoning_id),
	CONSTRAINT fk_order_seasonings_order_id FOREIGN KEY (order_id) REFERENCES orders (id),
	CONSTRAINT fk_order_seasonings_seasoning_id FOREIGN KEY (seasoning_id) REFERENCES seasonings (id)
);