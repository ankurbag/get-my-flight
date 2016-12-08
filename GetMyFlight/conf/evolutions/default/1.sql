# --- !Ups
CREATE TABLE "flight"("id" SERIAL PRIMARY KEY ,"source" varchar, "destination" varchar,"carrier" varchar,"dateOfTravel" varchar,"dateOfPriceFall" varchar,"predictedPrice" varchar,"latitude" varchar,"longitude" varchar,"distance" varchar,"seats" varchar);
INSERT INTO "flight" values (1,'Boston', 'New York','CA', '10/11/2016','10/11/2016', '1234','70.23343', '67.2323232323', '234', '340');
INSERT INTO "flight" values (2,'Boston', 'San Jose','CA', '10/11/2016','10/11/2016', '1234','70.23343', '67.2323232323', '234', '340');
INSERT INTO "flight" values (3,'Boston', 'Barcelona','CA', '10/11/2016','10/11/2016', '1234','70.23343', '67.2323232323', '234', '340');
INSERT INTO "flight" values (4,'Boston', 'Madrid','CA', '10/11/2016','10/11/2016', '1234','70.23343', '67.2323232323', '234', '340');
# --- !Downs

DROP TABLE "flight";
