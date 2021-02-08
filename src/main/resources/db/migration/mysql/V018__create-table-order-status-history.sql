CREATE TABLE IF NOT EXISTS order_status_history (
	id						BIGINT NOT NULL AUTO_INCREMENT,
	status					VARCHAR(30) NOT NULL,
	alter_date				DATETIME NOT NULL DEFAULT now(),
	order_id				BIGINT NOT NULL,
	CONSTRAINT pk_order_status_history_id PRIMARY KEY (id),
	CONSTRAINT fk_order_status_history_order_id FOREIGN KEY (order_id) REFERENCES orders (id)
);