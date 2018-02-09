CREATE KEYSPACE product WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

create table product.price (
  PARTITION_ID int,
  PRD_ID int,
  VALUE double,
  CURRENCY_CODE text,
  PRIMARY KEY (PARTITION_ID, PRD_ID)
);

INSERT into price (partition_id, prd_id, currency_code, value) values (1, 1380001, 'USD', 100.00);