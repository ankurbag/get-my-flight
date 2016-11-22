setwd("E:/Boston/NEU/Fall 2016/Additional/Scala/Scala_Project")
library("sqldf")
library(data.table)
library(plyr)
library(dplyr)
data_tkcarrier <- fread(input ="312849061_T_DB1B_MARKET.csv",header = TRUE)
data_carrier <- fread(input = "104091949_T_T100D_SEGMENT_ALL_CARRIER.csv",header = TRUE)
lookup <- fread(input ="312840607_T_MASTER_CORD.csv",header = TRUE)
uniquedata<- unique(lookup, by='AIRPORT_ID')
origin_sample<-sqldf("select * from test1 as a inner join uniquedata as b on b.AIRPORT_ID=a.ORIGIN_AIRPORT_ID")
cordinates<-sqldf("select a.origin_airport_id,c.latitude as ORIGIN_LATITUDE,c.longitude as ORIGIN_LONGITUDE,a.dest_airport_id,b.latitude as DES_LATITUDE,b.longitude as DEST_LONGITUDE from data_carrier as a inner join uniquedata as b on b.AIRPORT_ID=a.DEST_AIRPORT_ID inner join uniquedata as c on c.AIRPORT_ID=a.ORIGIN_AIRPORT_ID")
data_input_cordinates <- cbind(origin_sample[,-c(3,8,14:22)],cordinates)
test_data <- data_tkcarrier[1:10000,]
test_data2 <- data_input_cordinates[1:10000,]
setnames(test_data,"TICKET_CARRIER","CARRIER")
test.df <- sqldf("select * from test_data a INNER JOIN test_data2 b ON a.CARRIER=b.CARRIER")
test.df <- test.df[,-c(7)]
random <- sample(min(test.df$MARKET_FARE):max(test.df$MARKET_FARE),size,replace = TRUE)
test.df$MARKET_FARE <- random
seats <- sample(1:400,size,replace = TRUE)
test.df$SEATS <- seats
date_data <- test.df[,c("YEAR","QUARTER")]
size <- nrow(date_data)
DATE <- sample(seq(as.Date('2016/01/01'), as.Date('2016/03/31'), by="day"), size = size,replace = TRUE)
DAYS <- weekdays(as.Date(DATE,'%Y-%m-%d'))
MONTH <- months(DATE)
date_data <- cbind(date_data,DATE,DAYS,MONTH)
test.df <- test.df[,-c(1:2,5,15)]
test.df <- cbind(test.df,date_data)
write.csv(test.df,file = "2016_Quarter1.csv",row.names = FALSE)