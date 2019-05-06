package com.zhuzu.char05

import java.util.Properties

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame}


case class Peop(id: Int, name: String, age: Int)

object SparkContextDemo {


  def main(args: Array[String]): Unit = {
    //  初始化SparkContext
    // new SQLContext的方式创建SQLContext
    val conf = new SparkConf().setAppName("demo").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlc = new org.apache.spark.sql.SQLContext(sc)
    // 导入SQLContext的隐式转换函数toDF
    import sqlc.implicits._

    println("*" * 10)
    val prop = new Properties()
    prop.put("user", "root")
    prop.put("password", "root")
    prop.put("driver", "com.mysql.jdbc.Driver")
    //val frame: DataFrame = sqlc.read.jdbc("jdbc:mysql://localhost:3306/scm", "ym_fam", properties = prop)


    val peoRDD = sc.textFile("d:/s.txt").map(_.split(" ")).map(x => Peop(x(0).toInt, x(1), x(2).toInt))
    val peopleDF = peoRDD.toDF()
    // 将DataFrame注册成表
    //    peopleDF.createOrReplaceTempView("people") //高版本中是这个
    peopleDF.registerTempTable("people")
    // 通过SQLContext执行查询
    sqlc.sql("select * from people").show()
    sc.stop()
    // frame.show(3)

  }
}
