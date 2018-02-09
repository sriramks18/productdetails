create table product.price (
  PARTITION_ID int,
  PRD_ID int,
  VALUE double,
  CURRENCY_CODE text,
  PRIMARY KEY (PARTITION_ID, PRD_ID)
);
