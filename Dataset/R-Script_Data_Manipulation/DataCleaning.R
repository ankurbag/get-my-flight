#Setting the working directory to my local system
setwd("D:/Scala_Course/Project/Data")
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
data_tkcarrier <- fread(input ="Fare_Carrier.csv",header = TRUE)

#Data with the carrier, seats and source and destination details
data_carrier <- fread(input = "S&D.csv",header = TRUE)

#Creating Lookup data of Airport ID
lookup <- fread(input ="Longitude_Latitude.csv",header = TRUE)
uniquedata<- unique(lookup, by='AIRPORT_ID')

#joining tables
dest_sample<-sqldf("select * from data_carrier as a inner join uniquedata as b on b.AIRPORT_ID=a.DEST_AIRPORT_ID")
origin_sample<-sqldf("select * from dest_sample as a inner join uniquedata as b on b.AIRPORT_ID=a.ORIGIN_AIRPORT_ID")
cordinates<-sqldf("select a.origin_airport_id,c.latitude as ORIGIN_LATITUDE,c.longitude as ORIGIN_LONGITUDE,a.dest_airport_id,b.latitude as DES_LATITUDE,b.longitude as DEST_LONGITUDE from data_carrier as a inner join uniquedata as b on b.AIRPORT_ID=a.DEST_AIRPORT_ID inner join uniquedata as c on c.AIRPORT_ID=a.ORIGIN_AIRPORT_ID")
colnames(origin_sample)
data_input_cordinates <- cbind(origin_sample[,-c(3,8,14:21)],cordinates)
colnames(data_input_cordinates)

#write.csv(sample,file = "testfile.csv",row.names = FALSE)
#head(sample)
#setnames(test2,c("AIRPORT_ID","AIRPORT_ID.1","LATITUDE","LONGITUDE","LONGITUDE.1","LATITUDE.1"),c("DEST_AIRPORT_ID","ORG_AIRPORT_ID","DEST_LATITUDE","DEST_LONGITUDE","ORG_LONGITUDE","ORG_LATITUDE"))
#head(test2)
#Creating a new data frame which contains the first 10000 rows
# BETTER APPROACH TO USE SAMPLE FUCNTION. WILL ADD THAT IN THE NEXT UPDATED
# SCRIPT
# Test sample with data_opcarrier
test_data <- data_tkcarrier[1:10000,]

# Test sample with data_carrier
test_data2 <- data_input_cordinates[1:10000,]

# In opcarrier Data frame, the column OPERATING_CARRIER, is changed to CARRIER
# This is to maintain the same column name for all data frames
setnames(test_data,"TICKET_CARRIER","CARRIER")

# SQL DF you can run any SQL command by treating the Data frame as a TABLE

#test.df <- sqldf("select a.YEAR,a.QUARTER,a.MARKET_FARE,b.SEATS,b.CARRIER,b.ORIGIN_AIRPORT_ID,b.ORIGIN,b.ORIGIN_CITY_NAME,b.ORIGIN_STATE_ABR,b.ORIGIN_STATE_NM,b.DEST_AIRPORT_ID,b.DEST,b.DEST_CITY_NAME,b.DEST_STATE_ABR,b.DEST_STATE_NM,b.MONTH from test_data a INNER JOIN test_data2 b ON a.CARRIER=b.CARRIER")
test.df <- sqldf("select * from test_data a INNER JOIN test_data2 b ON a.CARRIER=b.CARRIER")
test.df <- test.df[,-c(7,17)]
colnames(test.df)

#Generate Random value for Market fare between min and max of the market price 
# for that quarter
date_data <- test.df[,c("YEAR","QUARTER")]
size <- nrow(date_data)
random <- sample(min(test.df$MARKET_FARE):max(test.df$MARKET_FARE),size,replace = TRUE)
test.df$MARKET_FARE <- random

# Generate Random value for number of available seats, between 1-400
seats <- sample(1:400,size,replace = TRUE)
test.df$SEATS <- seats

## DATE MANIPULATION ##################################
#New data frame with only year and quarter

#Size of the Data frame, to get the total number of rows


# Generating dates for a givenQuarter
## ***PLEASE CHANGE THE YEAR AND DATES BASED ON THE QUARTER YOU WISH TO GENERATE
DATE <- sample(seq(as.Date('2016/01/01'), as.Date('2016/03/31'), by="day"), size = size,replace = TRUE)

# Days for the date sequence
DAYS <- weekdays(as.Date(DATE,'%Y-%m-%d'))

#Month for the dates generated
MONTH <- months(DATE)

#Binding the date data together
date_data <- cbind(date_data,DATE,DAYS,MONTH)

#Removing the columsn YEAR AND QUARTER FROM THE INITIAL DATA SET
colnames(test.df)
test.df <- test.df[,-c(1:2,5,15)]

#FINAL DATA FRAME
test.df <- cbind(test.df,date_data)


## WRITING IT TO A CSV FILE
# PLEASE CHANGE THE NAME OF THE CSV FILE
write.csv(test.df,file = "2015_Quarter2.csv",row.names = FALSE)
summary(test.df)
dim(test.df)
colnames(test.df)

#Latitude and Long
library(geosphere)
#c<-distm (c(test.df$ORIGIN_LONGITUDE,test.df$ORIGIN_LATITUDE), c(test.df$DEST_LONGITUDE,test.df$DES_LATITUDE), fun = distVincentyEllipsoid)
#distHaversine(c(test.df$ORIGIN_LONGITUDE,test.df$ORIGIN_LATITUDE), c(test.df$DEST_LONGITUDE,test.df$DES_LATITUDE))
#names(test.df)
help(distVincentyEllipsoid)
for( i in 1:nrow(test.df)){
  test.df$Distance[i]<-distm (c(test.df$ORIGIN_LONGITUDE[i],test.df$ORIGIN_LATITUDE[i]), c(test.df$DEST_LONGITUDE[i],test.df$DES_LATITUDE[i]), fun = distVincentyEllipsoid)  
  
}

#Base Fare Calculation
min <- min(test.df$Distance)
max <- max(test.df$Distance)
mean <- mean(test.df$Distance)
mean

for( i in 1:nrow(test.df)){
  test[i]<-test.df$Distance[i]/mean*100
 if(test[i]<50)
 {test.df$BaseFare[i]<-50}

}
head(test.df$BaseFare)
max(test.df$BaseFare)
min(test.df$BaseFare)

