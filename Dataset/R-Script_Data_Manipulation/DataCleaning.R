#Setting the working directory to my local system
setwd("G:/Semester IV/Scala/Project/Datasets/2015_Quarter3")
# INSTALL THE PACKAGE FIRST TIME
#install.packages("sqldf")
library("sqldf")
#Identified the fastest way to read the file
#Fread is a function in data.table package which can be used to read a file
# very fast.
## CAN ALSO USE read.csv and read.table in case you want it

## JUST NEED TO INSTALL IT THE FIRST TIME
#install.packages("data.table")
#install.packages("plr")
library(data.table)
library(plyr)
library(dplyr)

# Data with the quarter and year details
data_opcarrier <- fread(input ="104154270_T_DB1B_MARKET.csv",header = TRUE)

#Data with the carrier, seats and source and destination details
data_carrier <- fread(input = "104154270_T_T100D_SEGMENT_ALL_CARRIER.csv",header = TRUE)

#Creating Lookup data of Airport ID
lookup <- fread(input ="317448616_T_MASTER_CORD.csv",header = TRUE)
uniquedata<- unique(lookup, by='AIRPORT_ID')

#joining tables
test1<-sqldf("select * from data_carrier as a inner join uniquedata as b on b.AIRPORT_ID=a.DEST_AIRPORT_ID")
test2<-sqldf("select * from test1 as a inner join uniquedata as b on b.AIRPORT_ID=a.ORIGIN_AIRPORT_ID")

colnames(test3)
setnames(test3,c("AIRPORT_ID","AIRPORT_ID.1","LATITUDE","LONGITUDE","LONGITUDE.1","LATITUDE.1"),c("DEST_AIRPORT_ID","ORG_AIRPORT_ID","DEST_LATITUDE","DEST_LONGITUDE","ORG_LONGITUDE","ORG_LATITUDE"))

#Creating a new data frame which contains the first 10000 rows
# BETTER APPROACH TO USE SAMPLE FUCNTION. WILL ADD THAT IN THE NEXT UPDATED
# SCRIPT
# Test sample with data_opcarrier
test_data <- data_opcarrier[1:10000,]

# Test sample with data_carrier
test_data2 <- data_carrier[1:10000,]

# In opcarrier Data frame, the column OPERATING_CARRIER, is changed to CARRIER
# This is to maintain the same column name for all data frames

setnames(test_data,"TkCarrier","CARRIER")

## Just a test sample to see the names of the columns for any data frame
colnames(test_data)



# SQL DF you can run any SQL command by treating the Data frame as a TABLE

test.df <- sqldf("select a.YEAR,a.QUARTER,a.MARKET_FARE,b.SEATS,b.CARRIER,b.ORIGIN_AIRPORT_ID,b.ORIGIN,b.ORIGIN_CITY_NAME,b.ORIGIN_STATE_ABR,b.ORIGIN_STATE_NM,b.DEST_AIRPORT_ID,b.DEST,b.DEST_CITY_NAME,b.DEST_STATE_ABR,b.DEST_STATE_NM,b.MONTH from test_data a INNER JOIN test_data2 b ON a.CARRIER=b.CARRIER")
size <-row(test.df)
size
#Generate Random value for Market fare between min and max of the market price 
# for that quarter
random <- sample(min(test.df$MARKET_FARE):max(test.df$MARKET_FARE),size,replace = TRUE)
test.df$MARKET_FARE <- random

# Generate Random value for number of available seats, between 1-400
seats <- sample(1:400,size,replace = TRUE)
test.df$SEATS <- seats

## DATE MANIPULATION ##################################
#New data frame with only year and quarter
date_data <- test.df[,c("YEAR","QUARTER")]
#Size of the Data frame, to get the total number of rows
size <- nrow(date_data)

# Generating dates for a givenQuarter
## ***PLEASE CHANGE THE YEAR AND DATES BASED ON THE QUARTER YOU WISH TO GENERATE
Date_Sample <- sample(seq(as.Date('2015/07/01'), as.Date('2015/09/31'), by="day"), size = size,replace = TRUE)

# Days for the date sequence
days <- weekdays(as.Date(Date_Sample,'%Y-%m-%d'))

#Month for the dates generated
month <- months(Date_Sample)

#Binding the date data together
date_data <- cbind(date_data,Date_Sample,days,month)

#Removing the columsn YEAR AND QUARTER FROM THE INITIAL DATA SET
test.df <- test.df[,-c(1:2,16)]

#FINAL DATA FRAME
test.df <- cbind(test.df,date_data)

## WRITING IT TO A CSV FILE
# PLEASE CHANGE THE NAME OF THE CSV FILE
write.csv(test.df,file = "2015_Quarter3.csv",row.names = FALSE)

