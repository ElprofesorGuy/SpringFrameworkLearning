drop table if exists beer;
drop table if exists customer;


create table beer (
                      id varchar(36) not null,
                      beer_name varchar(50) not null,
                      create_date TIMESTAMP,
                      update_date TIMESTAMP,
                      price decimal(38,2) not null,
                      quantity_on_hand integer,
                      version integer,
                      upc varchar(255) not null,
                      primary key(id)
);

create table customer(
                         id varchar(36) not null,
                         customer_name varchar(255),
                         create_date TIMESTAMP,
                         update_date TIMESTAMP,
                         version integer,
                         primary key(id)
);