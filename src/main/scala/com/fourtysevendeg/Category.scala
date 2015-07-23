package com.fourtysevendeg.jsonScalaPerftest

import java.io.File
import collection.immutable.HashMap

case class Category(directoryName: String, name: String) {

  def this(fn: String) = {
    this(fn, fn.substring(fn.lastIndexOf('/') + 1))
  }

  val datasets = Category.getFilesMatching(directoryName, fn => fn.isFile).map {
    each => new Dataset(each.getCanonicalPath)
  }

  override def toString = {
    val sb = new StringBuilder
    sb.append(name)
    sb.append(datasets.map(ds => ds.toString).mkString(": ", ", ", ""))
    sb.toString()
  }

  def measure(operation:String, adaptor: LibraryAdaptor, iterations: Int, warmUp: Int): Long = {
    datasets.foldLeft(0L)( (prevnanos,d) => {
      print(s"($operation,${name},${adaptor.getName}) Warming up... ")
      // ignore warmup result
      adaptor.measure(d, operation, warmUp)
      print(" Measuring...")
      val nanos = adaptor.measure(d, operation, iterations)
      val ms = nanos.toDouble / 1000000
      println(f"Completed $ms%.3f ms")

      /*
      val preop = if (operation.startsWith("un")) operation.substring(2) else ""
      val pre = if (preop == "") 0L else {
        print(s"($preop,${name},${adaptor.getName}) Warming up... ")
        // ignore warmup result
        adaptor.measure(d, preop, warmUp)
        print(" Measuring...")
        val nanos = adaptor.measure(d, preop, iterations)
        val ms = nanos.toDouble / 1000000
        println(f"Completed $ms%.3f ms")
        nanos
      }
      */
      val pre = 0
      prevnanos + nanos - pre
    })
  }
}

object Category {
  def getFilesMatching(path: String, p: File => Boolean) = {
    val dir = new java.io.File(path).getAbsolutePath
    new File(dir).listFiles.filter(file => p(file))
  }
}
