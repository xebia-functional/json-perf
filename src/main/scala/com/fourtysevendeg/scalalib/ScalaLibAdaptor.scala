package com.fourtysevendeg.jsonScalaPerftest.scalalib

import com.fourtysevendeg.jsonScalaPerftest.LibraryAdaptor
import scala.util.parsing.json.{JSONType, JSON}

class ScalaLibAdaptor(name: String) extends LibraryAdaptor(name) {


  override def initialize() { /* nop */ }

  override def parseOnce(json: String) = {
    //JSON.parseFull(json)
    time(JSON.parseRaw(json))
  }

  override def mapTweet(json: String) = 0L

  override def hasMap = false

  override def hasUnparse: Boolean = false

  override def unparseOnce(json: String) = {
    0L
  }

  override def hasUnmap: Boolean = false
}
