package edu.neu.coe.scala.spark.nn

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.mllib.util.MLUtils


/**
 * @author scalaprof
 */
object PerceptronClassifier2 extends App {

	val conf = new SparkConf().setMaster("local").setAppName("spam")
	val sc = new SparkContext(conf)
	val sqlContext = new org.apache.spark.sql.SQLContext(sc)
	val sparkHome = "/Spark_Project/spark-2.0.2-bin-hadoop2.7/"//"/Applications/spark-1.5.1-bin-hadoop2.6/" // D:\Softwares\spark-2.0.2-bin-hadoop2.7\spark-2.0.2-bin-hadoop2.7
	/*
	val trainingFile = "sample_multiclass_classification_data.txt"

	// this is used to implicitly convert an RDD to a DataFrame.
	import sqlContext.implicits._

	// Load training data
	val data = MLUtils.loadLibSVMFile(sc, s"$trainingFile").toDF()
	// Split the data into train and test
	val splits = data.randomSplit(Array(0.6, 0.4), seed = 1234L)
	val train = splits(0)
	val test = splits(1)
	// specify layers for the neural network: 
	// input layer of size 4 (features), two intermediate of size 5 and 4 and output of size 3 (classes)
	val layers = Array[Int](4, 5, 4, 3)
	// create the trainer and set its parameters
	val trainer = new MultilayerPerceptronClassifier()
			.setLayers(layers)
			.setBlockSize(128)
			.setSeed(1234L)
			.setMaxIter(100)
	// train the model
	val model = trainer.fit(train)
	// compute precision on the test set
	val result = model.transform(test)
	val predictionAndLabels = result.select("prediction", "label")
  predictionAndLabels.show
	val evaluator = new MulticlassClassificationEvaluator()
			.setMetricName("precision")
	println("Precision:" + evaluator.evaluate(predictionAndLabels))

*/
	//////////

	//imports for ML
	import com.databricks.spark.csv._
	import org.apache.spark.mllib.linalg.{Vector, Vectors}
	import org.apache.spark.sql.functions.udf
	//load data
	val data2 = sqlContext.csvFile("test.csv")

	//Rename any one column to features
	//val DF2 = data2.withColumnRenamed("CARRIER", "features")
	val DF2 = data2.select("MARKET_FARE","SEATS","ORIGIN_AIRPORT_ID");

	//scala> DF2.take(2)
	//res6: Array[org.apache.spark.sql.Row] = Array([0,0,0], [0,0,1628859.542])

	//define doublelfunc
	val toDouble = udf[Double, String]( _.toDouble)

	//Convert all to double
	val featureDf = DF2
		.withColumn("MARKET_FARE",toDouble(DF2("MARKET_FARE")))
		.withColumn("SEATS",toDouble(DF2("SEATS")))
		.withColumn("ORIGIN_AIRPORT_ID",toDouble(DF2("ORIGIN_AIRPORT_ID")))
		.select("MARKET_FARE","SEATS","ORIGIN_AIRPORT_ID")


	//Define the format
	val toVec4 = udf[Vector, Double,Double] { (v1,v2) => Vectors.dense(v1,v2) }

	//Format for features which is gst_id_matched
	val encodeLabel   = udf[Double, String]( _ match
	{ case "0.0" => 0.0 case "1.0" => 1.0} )

	//Transformed dataset
	val df = featureDf
		.withColumn("features",toVec4(featureDf("ORIGIN_AIRPORT_ID"),featureDf("SEATS")))
		.withColumn("label",encodeLabel(featureDf("MARKET_FARE")))
		.select("label", "features")

	val splits = df.randomSplit(Array(0.6, 0.4), seed = 1234L)
	val train = splits(0)
	val test = splits(1)
	// specify layers for the neural network:
	// input layer of size 4 (features), two intermediate of size 5 and 4 and output of size 3 (classes)
	val layers = Array[Int](4, 5, 4, 3)
	// create the trainer and set its parameter


	val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(12).setSeed(1234L).setMaxIter(10)
	// train the model
	val model = trainer.fit(train)

	val result = model.transform(test)
	val predictionAndLabels = result.select("prediction", "label")
	predictionAndLabels.show
	val evaluator = new MulticlassClassificationEvaluator()
		.setMetricName("precision")
	println("Precision:" + evaluator.evaluate(predictionAndLabels))

}