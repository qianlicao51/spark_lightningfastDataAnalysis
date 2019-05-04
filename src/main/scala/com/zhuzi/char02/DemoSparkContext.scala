package com.zhuzi.char02

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

class DemoSparkContext {
  //  初始化SparkContext
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)

  def main(args: Array[String]): Unit = {
    println(sc)
  }


}
