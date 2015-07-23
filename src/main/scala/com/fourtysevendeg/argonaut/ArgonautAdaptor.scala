package com.fourtysevendeg.jsonScalaPerftest.argonaut

import com.fourtysevendeg.jsonScalaPerftest.LibraryAdaptor
import scalaz._
import Scalaz._
import argonaut._
import Argonaut._

class ArgonautAdaptor(name: String) extends LibraryAdaptor(name) {
  override def hasMap: Boolean = false

  override def parseOnce(json: String) = {
    time(Parse.parse(json))
  }

  override def mapTweet(json: String): Long = {0L}

  override def initialize(): Unit = {}

  override def hasUnparse: JsonBoolean = true

  override def unparseOnce(json: String) = {
    val x: \/[String,Json] = Parse.parse(json)
    time(x.getOrElse(null).nospaces)
  }

  override def hasUnmap: JsonBoolean = false
}
