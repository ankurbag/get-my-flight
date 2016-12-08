package org.deeplearning4j.examples
import scala.io._
/**
  * Created by Ankur on 12/6/2016.
  */

  object CSVDemo extends App {
    println("Month, Income, Expenses, Profit")
    val bufferedSource =scala.io.Source.fromFile("test1.csv")
    for (line <- bufferedSource.getLines.drop(1)) {
    val cols = line.split(",").map(_.trim)
    // do whatever you want with the columns here
    println(s"${cols(0)}|${cols(1)}|${cols(2)}|${cols(3)}")
  }
  bufferedSource.close
  }
