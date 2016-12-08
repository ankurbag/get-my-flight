package org.deeplearning4j.examples.feedforward.regression

import java.util.{Collections, List, Random}

import org.apache.avro.SchemaBuilder.ArrayBuilder
import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator
import org.deeplearning4j.examples.feedforward.regression.function.MathFunction
import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.layers.{DenseLayer, OutputLayer}
import org.deeplearning4j.nn.conf.{NeuralNetConfiguration, Updater}
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.dataset.DataSet
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.lossfunctions.LossFunctions
import scala.collection.mutable.ArrayBuilder
/**
 * Created by Anwar on 3/15/2016.
 * An example of regression neural network for performing addition
 */
object RegressionFlightDataset {
    //Random number generator seed, for reproducability
    val seed = 12345
    //Number of iterations per minibatch
    val iterations = 1
    //Number of epochs (full passes of the data)
    val nEpochs = 30
    //Number of data points
    val nSamples = 30
    //Batch size: i.e., each epoch has nSamples/batchSize parameter updates
    val batchSize = 30
    //Network learning rate
    val learningRate = 0.01
    // The range of the sample data, data in range (0-1 is sensitive for NN, you can try other ranges and see how it effects the results
    // also try changing the range along with changing the activation function
    val MIN_RANGE = 0
    val MAX_RANGE = 3

    val rng = new Random(seed)

    def main(args: Array[String]): Unit = {

        //Generate the training data
        val iterator = getTrainingData(batchSize,rng)

        //Create the network
        //val numInput = 2
      //  val numOutputs = 1
       // val nHidden = 10
        val numInput = 3
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
            net.fit(iterator)
        }
        // Test the addition of 2 numbers (Try different numbers here)
        //val input = Nd4j.create(Array[Double](2.434136744,56.46941979), Array[Int](nSamples, 1))

       //All Dates for one month
        val input = Nd4j.create(Array[Double](0.510732219,2,245), Array[Int](1, 3))
        val out = net.output(input, false) // (D,M) -> TF
        //compare with sql
        System.out.println(out)
        System.out.println(net.error(out))

    }


   def getTrainingData(batchSize: Int, rand: Random): DataSetIterator = {
        val carrerinput1Builder = Array.newBuilder[Double]
        val sourceinput2Builder = Array.newBuilder[Double]
       val destinationinput3Builder = Array.newBuilder[Double]

       val outputBuilder = Array.newBuilder[Double]
        carrerinput1Builder +=1.623131698+=1.034586203+=1.034586203 += 0.245888196+=2.32906497+=2.32906497+=2.139762969+=2.139762969+=1.798566305+=1.455761879+=1.062607039+=0.036242233+=1.798566305+=0.520032548+=1.455761879+=2.434136744+=1.798566305+=0.510732219+=0.510732219+=2.434136744+=0.520032548+=2.32906497+=0.042867703+=1.798566305+=0.042867703+=1.455761879+=2.32906497+=0.520032548+= -0.345740171+=0.510732219
        sourceinput2Builder +=42 +=33 +=20 +=25 +=26 +=19 +=56 +=15 +=12 +=10 +=26 +=41 +=12 +=6 +=67 +=107 +=12 +=14 +=13 +=48 +=33 +=91 +=1 +=25 +=24 +=32 +=129 +=60 +=29 +=17
        destinationinput3Builder +=58 +=88 +=16 +=22 +=38 +=16 +=1 +=6 +=11 +=17 +=36 +=37 +=40 +=86 +=42 +=90 +=58 +=135 +=89 +=42 +=116 +=5 +=3 +=14 +=29 +=33 +=8 +=11 +=59 +=41


       outputBuilder +=81.57600414+=61.91704679+=269.2031972+=131.3192+=88.13454973+=112.7491952+=262.2201087+=83.16723957+=118.1951+=156.4520796+=58.62589305+=78.03415241+=97.19012+=81.64208+=124.3487011+=73.72120588+=58.44251+=186.9097534+=238.688857+=173.8194753+=93.68026+=95.2847778+=77.8045789+=256.8281+=142.921956+=219.5181053+=189.7294184+=72.89325+=70.38616069+=58.48450893
        /*(0 until nSamples).foreach { i =>
            val i1 = MIN_RANGE + (MAX_RANGE - MIN_RANGE) * rand.nextDouble()
            val i2 =  MIN_RANGE + (MAX_RANGE - MIN_RANGE) * rand.nextDouble()
            input1Builder += i1
            input2Builder += i2
            sumBuilder += i1 + i2
        }*/
        val inputNDArray1 = Nd4j.create(carrerinput1Builder.result(), Array[Int](nSamples,1))
        val inputNDArray2 = Nd4j.create(sourceinput2Builder.result(), Array[Int](nSamples,1))
       val inputNDArray3 = Nd4j.create(destinationinput3Builder.result(), Array[Int](nSamples,1))
       val outputNDArray = Nd4j.create(outputBuilder.result(), Array[Int](nSamples,1))

       val inputNDArray = Nd4j.hstack(inputNDArray1,inputNDArray2,inputNDArray3)
        /*val outPut = Nd4j.create(sumBuilder.result(), Array[Int](nSamples, 1))
        val dataSet = new DataSet(inputNDArray, outPut)
        val listDs: java.util.List[DataSet] = dataSet.asList()
        Collections.shuffle(listDs,rng)
        new ListDataSetIterator(listDs,batchSize)*/

       val allData: DataSet = new DataSet(inputNDArray,outputNDArray)

       val list: List[DataSet] = allData.asList()
       Collections.shuffle(list)
       new ListDataSetIterator(list,batchSize)

    }
}
