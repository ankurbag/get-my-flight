package edu.neu.deeplearning4j.feedforward.regression

import java.util.{Collections, List, Random}

import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator
import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.layers.{DenseLayer, OutputLayer}
import org.deeplearning4j.nn.conf.{NeuralNetConfiguration, Updater}
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.nd4j.linalg.dataset.DataSet
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.lossfunctions.LossFunctions

/**
  * Created by Ankur on 12/9/2016.
  * An example of regression neural network for performing addition
  */
object RegressionFlightDataset {
  //Random number generator seed, for reproducability
  val seed = 12345
  //Number of iterations per minibatch
  val iterations = 1
  //Number of epochs (full passes of the data)
  val nEpochs = 1000
  //Number of data points
  val nSamples = 1000
  //Batch size: i.e., each epoch has nSamples/batchSize parameter updates
  val batchSize = 1000
  //Network learning rate
  val learningRate = 0.01
  // The range of the sample data, data in range (0-1 is sensitive for NN, you can try other ranges and see how it effects the results
  // also try changing the range along with changing the activation function
  val ntrain = 35000
  val MIN_RANGE = 0
  val MAX_RANGE = 3

  val rng = new Random(seed)

  def main(args: Array[String]): Unit = {
    //Generate the training data
    val iterator = getTrainingData(batchSize, rng)
    val numInput = 7
    val numOutputs = 1
    val nHidden = 4
    val net: MultiLayerNetwork = new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
      .seed(seed)
      .iterations(iterations)
      .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
      .learningRate(learningRate)
      .weightInit(WeightInit.XAVIER)
      .updater(Updater.NESTEROVS).momentum(0.9)
      .list()
      .layer(0, new DenseLayer.Builder().nIn(numInput).nOut(nHidden)
        .activation("tanh")
        .build())
      .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
        .activation("identity")
        .nIn(nHidden).nOut(numOutputs).build())
      .pretrain(false).backprop(true).build()
    )
    net.init()
    net.setListeners(new ScoreIterationListener(1))

    //Train the network on the full data set, and evaluate in periodically
    (0 until nEpochs).foreach { _ =>
      iterator.reset()
      print(net.fit(iterator))
      //print()
    }
    //Create the network and Test
    testData(net,iterator)
  }

  /**
    * Test data against the Trained Dataset
    * @param net
    * @param iterator
    */
  def testData(net:MultiLayerNetwork,iterator : DataSetIterator): Unit ={

    //Testing Phase
    //Read CSV
    val bufferedSource = scala.io.Source.fromFile("test.csv")

    val z:Array[Double] = new Array[Double](ntrain)

    var i=0
    for (line <- bufferedSource.getLines.drop(1)) {
      val cols = line.split(",").map(_.trim)
      val input = Nd4j.create(Array[Double](cols(1).toDouble,cols(2).toDouble,cols(3).toDouble,cols(4).toDouble,cols(5).toDouble,cols(6).toDouble,cols(7).toDouble), Array[Int](1, 7))
      val out = net.output(input, false)
      val outdata = out.getDouble(0,0)
      System.out.println("|" + cols(0).toDouble + "|" +outdata + "|")
      // estimate accuracy
      z(i) = mse(cols(0).toDouble,outdata)
      i=i+1;
      //Insert to Db
      //val params = Array(cols(1), "New York","CA", "10/11/2016","10/11/2016", "1234","256.8","70.23343", "67.2323232323")
      val params = Array(cols(11), cols(12),cols(10), cols(8),cols(9),  cols(0).toString,outdata.toString,"70.23343", "67.2323232323",cols(17).toString,cols(18).toString,cols(19).toString,cols(20).toString)
      DAO.insertToDb(params)
    }
    bufferedSource.close
    println("RMS Error : " + rms(z))

  }
  /**
    * Returns Root Mean Square
    * @param nums
    * @return
    */
  def rms(nums: Seq[Double]) = math.sqrt( ((nums).sum / nums.size))

  /**
    * Returns Mean Square Error
    * @param num1
    * @param num2
    * @return
    */
  def mse(num1: Double,num2:Double):Double = math.pow((num2 - num1),2)

  /**
    * Generate Training Data
    * @param batchSize
    * @param rand
    * @return
    */
  def getTrainingData(batchSize: Int, rand: Random): DataSetIterator = {

    //Input Parameters
    val carrerinput1Builder = Array.newBuilder[Double]
    val destinationinput2Builder = Array.newBuilder[Double]
    val sourceinput3Builder = Array.newBuilder[Double]
    val baseFareinput4Builder = Array.newBuilder[Double]
    val monthsInput5Builder = Array.newBuilder[Double]
    val daysInput6Builder = Array.newBuilder[Double]
    val seatInput7Builder = Array.newBuilder[Double]

    //Output Label
    val outputTicketFareBuilder = Array.newBuilder[Double]

    //Read CSV
    val bufferedSource = scala.io.Source.fromFile("train_nn.csv")
    for (line <- bufferedSource.getLines.drop(1)) {
      val cols = line.split(",").map(_.trim)
      // do whatever you want with the columns here
      carrerinput1Builder += cols(1).toDouble
      destinationinput2Builder += cols(2).toDouble
      sourceinput3Builder += cols(3).toDouble
      baseFareinput4Builder += cols(4).toDouble
      monthsInput5Builder += cols(5).toDouble //5 for Normalized data
      daysInput6Builder += cols(6).toDouble //6 for Normalized data
      seatInput7Builder += cols(7).toDouble
      outputTicketFareBuilder  += cols(0).toDouble
    }
    bufferedSource.close

    val inputNDArray1 = Nd4j.create(carrerinput1Builder.result(), Array[Int](nSamples, 1))
    val inputNDArray2 = Nd4j.create(destinationinput2Builder.result(), Array[Int](nSamples, 1))
    val inputNDArray3 = Nd4j.create(sourceinput3Builder.result(), Array[Int](nSamples, 1))
    val inputNDArray4 = Nd4j.create(baseFareinput4Builder.result(), Array[Int](nSamples, 1))
    val inputNDArray5 = Nd4j.create(monthsInput5Builder.result(), Array[Int](nSamples, 1))
    val inputNDArray6 = Nd4j.create(daysInput6Builder.result(), Array[Int](nSamples, 1))
    val inputNDArray7 = Nd4j.create(seatInput7Builder.result(), Array[Int](nSamples, 1))

    val outputNDArray = Nd4j.create(outputTicketFareBuilder.result(), Array[Int](nSamples, 1))

    val inputNDArray = Nd4j.hstack(inputNDArray1, inputNDArray2, inputNDArray3,inputNDArray4,inputNDArray5,inputNDArray6,inputNDArray7)
    val allData: DataSet = new DataSet(inputNDArray, outputNDArray)
    val list: List[DataSet] = allData.asList()
    Collections.shuffle(list)
    new ListDataSetIterator(list, batchSize)

  }
}
