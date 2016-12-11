package edu.neu.deeplearning4j.feedforward.regression

import java.util.Random

import org.nd4j.linalg.factory.Nd4j
import org.scalatest.{FlatSpec, Matchers}

import scala.io.{Codec, Source}
import scala.util.Success

/**
  * Created by Ankur on 12/9/2016.
  */
class RegressionFlightDatasetSpec extends FlatSpec with Matchers{

  behavior of "RegressionFlightDataset.getTrainingData"

  it should "work for the sample file" in {
    implicit val codec = Codec.UTF8
    val source = Source.fromFile("train_nn.csv")
    val trained =RegressionFlightDataset.getTrainingData(1000, new Random(1234L))
    trained.totalExamples() shouldBe  1000 //Number of Data samples taken as 1000
    source.close()
  }

  behavior of "DAO.insert"

  it should "work for the sample data" in {
    val params = Array("Boston", "New York","CA", "10/11/2016","10/11/2016", "1234","256.8","70.23343", "67.2323232323","1","2","3","4")
    DAO.insertToDb(params) shouldBe 1
  }

}
