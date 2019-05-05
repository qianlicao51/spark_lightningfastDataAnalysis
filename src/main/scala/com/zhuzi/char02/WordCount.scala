package com.zhuzi.char02

import org.apache.spark.{SparkConf, SparkContext}

import scala.reflect.io.File

object WordCount {

  //  初始化SparkContext
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)

  val input = sc.textFile("d:/words")

  //切分单词
  val words = input.flatMap(line => line.split(" "))
  val counts = words.map(word => (word, 1)).reduceByKey(_ + _)
  //  counts.saveAsTextFile("wordcount")
  private val tuples: Array[(String, Int)] = counts.collect()
  counts.saveAsSequenceFile("d:/wc")
  tuples.foreach(t => {
    println(t._1 + "的个数是" + t._2)
  })
  def main(args: Array[String]): Unit = {

  }
}
