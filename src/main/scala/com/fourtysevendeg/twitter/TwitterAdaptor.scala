package com.fourtysevendeg.jsonScalaPerftest.twitter

import com.twitter.json.Json.parse
import com.fourtysevendeg.jsonScalaPerftest.LibraryAdaptor

class TwitterAdaptor(name:String) extends LibraryAdaptor(name) {

  def initialize() {}
  
  def parseOnce(json: String) = {
      time(parse(json))
  }

  override def mapTweet(json: String) = 0L

  override def hasMap = false

  override def hasUnparse: Boolean = false

  override def unparseOnce(json: String) = 0L

  override def hasUnmap: Boolean = false
}
