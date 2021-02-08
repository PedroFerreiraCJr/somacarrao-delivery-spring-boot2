ALTER TABLE orders ADD COLUMN client_id BIGINT NOT NULL;

ALTER TABLE orders ADD CONSTRAINT orders_client_id FOREIGN KEY (client_id) REFERENCES users (id);
