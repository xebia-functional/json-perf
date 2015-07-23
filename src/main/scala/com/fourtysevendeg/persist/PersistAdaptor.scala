package com.fourtysevendeg.jsonScalaPerftest.persist

import com.persist.JsonOps._
import com.persist.JsonMapper._
import com.persist.json._

import com.fourtysevendeg.jsonScalaPerftest.LibraryAdaptor
import com.fourtysevendeg.jsonScalaPerftest.domain.{Place, Tweet}

class PersistAdaptor(name: String) extends LibraryAdaptor(name) {

  override def initialize() {
    /* nop */
  }

  def parseOnce(json: String) = {
    time(Json(json))
  }

  def unparseOnce(json: String) = {
    val j = Json(json)
    val t = time(Compact(j, sort = false))
    t
  }

  override def mapTweet(json: String) = {
    time(ToObject[Tweet](Json(json)))
  }

  override def mapPlace(json: String) = {
    time(ToObject[Place](Json(json)))
  }

  def hasMap = true

  def hasUnparse = true

  def hasUnmap = true
}
