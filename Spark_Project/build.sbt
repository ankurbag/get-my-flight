name := "Spark_Project"

version := "1.0"
/*
scalaVersion := "2.10.4"

val spark = "org.apache.spark"
val sparkVersion = "1.5.1"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
libraryDependencies += spark %% "spark-core" % sparkVersion % "provided"
libraryDependencies += spark %% "spark-mllib" % sparkVersion % "provided"
// https://mvnrepository.com/artifact/org.apache.spark/spark-sql_2.10
libraryDependencies += spark % "spark-sql_2.10" % "1.5.2"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"
*/

scalaVersion := "2.11.6"
libraryDependencies ++= Seq(
"org.scalatest" %% "scalatest" % "2.2.4" % "test",
"org.apache.spark" % "spark-core_2.11" % "1.6.1",
"org.apache.spark" % "spark-mllib_2.11" % "1.6.1"
)
// https://mvnrepository.com/artifact/com.databricks/spark-csv_2.10
libraryDependencies += "com.databricks" % "spark-csv_2.10" % "1.5.0"


