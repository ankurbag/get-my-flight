# --- !Ups
CREATE TABLE "flight"("id" SERIAL PRIMARY KEY ,"source" varchar, "destination" varchar,"carrier" varchar,"monthOfTravel" varchar,"dayOfTravel" varchar,"actualPrice" varchar,"predictedPrice" varchar);




# --- !Downs

DROP TABLE "flight";
