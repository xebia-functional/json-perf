package com.fourtysevendeg.jsonScalaPerftest

abstract class LibraryAdaptor(name: String) {

  //val timerName = "%s-main".format(name)
  //val className = getClass.getSimpleName

  def getName = name

  def hasMap: Boolean

  def hasUnparse: Boolean

  def hasUnmap: Boolean

  def initialize()

  def parseOnce(json: String): Long

  def unparseOnce(json: String): Long

  def mapTweet(json: String): Long

  def mapPlace(json: String): Long = {
    0L
    /* nop */
  }

  def time(f: => Unit): Long = {
    val t0 = System.nanoTime()
    f
    val t1 = System.nanoTime()
    t1 - t0
  }

  def measure(dataset: Dataset, operation: String, iterations: Int): Long = {
    initialize()

    //val context2 = mainTimer.time()

    val mapper: String => Long = dataset.name match {
      case "100tweets" => mapTweet
      case _ => mapPlace
    }

    val f: String => Long = operation match {
      case "parse" => parseOnce
      case "map" => mapper
      case "unparse" => unparseOnce
      case "unmap" => null
    }

    try {
      var t: Long = 0
      //val t0 = System.nanoTime()
      for (count <- 1 to iterations) {
        for (doc <- dataset.docs) {
          val t1 = f(doc)
          t += t1
        }
      }
      //val t1 = System.nanoTime()
      //(t1 - t0)
      t
    } catch {
      case ex: Exception => {
        println("%s bombed while processing %s (%s)".format(name, dataset.name, ex.getMessage))
        0L
      }
    }
  }
}
