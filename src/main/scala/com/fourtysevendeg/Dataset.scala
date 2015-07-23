package com.fourtysevendeg.jsonScalaPerftest

import io.Source

case class Dataset(fileName: String, name: String) {

  def this(fn: String) = {
    this(fn, fn.substring(fn.lastIndexOf('/') + 1, fn.lastIndexOf('.')))
  }

  val docs: List[String] = Source.fromFile(fileName).getLines().toList

  override def toString = {
    val sb = new StringBuilder
    sb.append(name)
    sb.append(" (")
    sb.append(docs.length)
    sb.append(" documents)")
    sb.toString()
  }
}
