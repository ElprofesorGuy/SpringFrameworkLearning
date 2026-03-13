drop table if exists beer_order_shipment;

CREATE TABLE beer_order_shipment
(
    id varchar(36) NOT NULL PRIMARY KEY,
    beer_order_id varchar(36) UNIQUE,
    tracking_number varchar(50),
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT NULL,
    version bigint DEFAULT NULL,
    CONSTRAINT bos_pk FOREIGN KEY (beer_order_id) references beer_order(id)
);

ALTER TABLE beer_order
    ADD COLUMN beer_order_shipment_id VARCHAR(36);

ALTER TABLE beer_order
    ADD CONSTRAINT bos_shipment_fk
        FOREIGN KEY (beer_order_shipment_id) references beer_order_shipment(id);
