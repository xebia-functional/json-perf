package com.fourtysevendeg.jsonScalaPerftest.jawn

import com.fourtysevendeg.jsonScalaPerftest.LibraryAdaptor
import jawn.Parser._
import jawn.ast.JawnFacade

class JawnAdaptor(name: String) extends LibraryAdaptor(name) {
  override def hasMap: Boolean = false

  override def parseOnce(json: String) = {
    time(parseFromString(json)(JawnFacade))
  }

  override def mapTweet(json: String) = {0L}

  override def initialize(): Unit = {}

  override def unparseOnce(json: String) = {
    val x = parseFromString(json)(JawnFacade).get
    time(x.render())
  }

  override def hasUnparse: Boolean = true

  override def hasUnmap: Boolean = false
}
