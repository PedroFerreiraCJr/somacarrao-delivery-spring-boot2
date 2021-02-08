CREATE TABLE IF NOT EXISTS order_ingredients (
	order_id				BIGINT NOT NULL,
	ingredient_id			BIGINT NOT NULL,
	CONSTRAINT pk_order_ingredients PRIMARY KEY (order_id, ingredient_id),
	CONSTRAINT fk_order_ingredients_order_id FOREIGN KEY (order_id) REFERENCES orders (id),
	CONSTRAINT fk_order_ingredients_ingredient_id FOREIGN KEY (ingredient_id) REFERENCES ingredients (id)
);