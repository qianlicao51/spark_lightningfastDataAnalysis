# Spsrk快速大数据分析

书中代码 https://github.com/databricks/learning-spark

## 2 Spark下载与入门
    基于spark1.6来学习本书


​    



## 3 RDD编程

​	在Spark中，对数据的所有操作不外乎创建RDD，转化RDD以及调用RDD操作进行求值，Spark将自动将RDD中的数据分发到集群上，并将操作并行化执行。



​	spark中take()获取了RDD的少量元素，然后在本地遍历这些元素，并在驱动端打印出来。RDD还有一个collect()函数，用来获取整个RDD的数据。如果程序把RDD筛选到一个很小的规模，并且想本地处理这些数据是，就可以使用它。`只有当你的而整个数据集能在单台机器内存中存放得下时，才能使用collect()，collect()不能用在大规模的数据集上`

​	

### 3.5 常见的转化操作和行动操作



#### 3.5.1 基本RDD

##### 1 针对各个元素的转化操作

​	最常用的转化操作是map和filter。

​	

​	如果希望每个输入元素生成多个输出元素。实现该功能的操作叫做faltmap()。该函数返回值序列的迭代器。输出的RDD倒不是有迭代器组成的。得到的是一个包含各个迭代器可访问的所有元素的RDD。flatMap()的一个简单用途是把输入的字符串切分为单词。



##### 2 伪集合操作

​	RDD本身不是严格的集合，但它也支持许多数学上的集合操作，比如合并相交。

​	RDD中最常缺失的集合属性是元素的唯一性，因为常常有重复的元素。如果只要唯一的元素，可以使用RDD.distinct()操作，distinct()操作的开销很大，因为它需要将所有数据通过网络进行混洗(shuffle)，以确保每个元素都只有一部分。



​	最简单的集合操作是union()，它会返回一个包含两个RDD钟所有元素的RDD。(如果有重复数据，Spark的union也会包含这些重复数据)

​	intersection(other) 只返回两个RDD都有的元素。运行时会去掉所有重复元素。所有它性能很差。

![](pic/A001.jpg)



#### 3.5.2 在不同RDD类型间转换

​	

##### 1 scala

	在Scala中，将RDD转为由特定函数的RDD是由饮食转换来自动处理的。需要加水import org.apache.spark.SparkContext._ 来使用这些隐式转换。可以在SparkContext对象的Scala文档(http://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.SparkContext$)查看所列出的隐式转换。这些隐式转换将一个RDD转为各种封装类，





## 4 键值操作

​	键值对RDD通常用来聚合计算。一般通过通过一些初始ETL操作间数据转化为键值对形式，针对RDD提供了一些操作接口。



![](pic/A002.jpg)

![](pic/A003.jpg)

```scala

```



​	有时，希望在分组操作和聚合操作之外的操作中改变RDD分区。对于这样的情况，spark提供了repartition()函数。它会把数据通过网络进行混洗，并创建出新的分区集合。`对数据重新分区的代价相对比大`，Spark中也有一个优化版的repartition()，也叫coalesce()。



#### 4.3.2 数据分组

​	对于有键的数据，常见的用例就是将数据根据键进行分组。

​	如果数据已经有预期的方式提取了键，groupByKey()就会使用RDD中的键来对数据进行分组，对于一个由类型K的键和类型V的值组成的RDD，所得到的RDD类型是[k,Iterable[v]]。

​	

#### 4.3.4 数据排序

​	经常要将RDD倒叙排列，因此sortByKey()函数接收一个叫做asceding的参数，表示我们时候想让结果按升序排序(默认是true)。有时可以按照完全不同的排序依据进行排序，可以自定义排序。



![](pic/A005.jpg)



### 4.5 数据分区



#### 4.5.1 获取RDD的分区方式

​	在Scala和java中，使用RDD的partitioner属性来获取RDD的分区方式。它会返回一个scala.Option对象，这是scala中用来存放可能存在的对象的容器类。可以对这个Option对象调用isFefined()来检查书否有值，调用get()来获取其中的值。



## 5 数据读取和保存



##### 1 文件格式和文件系统

​	对于存储在本地文件系统或分布式文件系统中的数据，Spark可以访问许多不同的文件格式，包括文本文件，JSON SequenctFile以及protocol buffer。

##### 2 Spark SQL中的结构化数据源

​	Spark SQL针对JSON和Apache Hive在内的结构化数据源。

##### 3 数据库与键值存储

 数据可以用来连接Cassandra HBase Elasticsearch 以及JDBC源。

### 5.2 文件格式



![](pic/A006.jpg)



#### 5.2.1 文本文件



​	spark支持读取给定目录的所有文件，以及输入路径中使用通配符(如part-*.txt)。大规模数据集通常存放在多个文件中，因此这一特性很有用。





#### 5.2.2 JSON

