package com.zhuzi.char02

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {

  //  初始化SparkContext
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)

  val input = sc.textFile("d:/words")
  //切分单词
  val words = input.flatMap(line => line.split(""))
  val counts = words.map(word => (word, 1)).reduceByKey(_ + _)
  counts.saveAsTextFile("wordcount")

  def main(args: Array[String]): Unit = {

  }
}
