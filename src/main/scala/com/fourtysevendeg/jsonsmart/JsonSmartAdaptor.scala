package com.fourtysevendeg.jsonScalaPerftest.jsonsmart

import com.fourtysevendeg.jsonScalaPerftest.LibraryAdaptor
import net.minidev.json.JSONValue
import net.minidev.json.JSONValue.parse

class JsonSmartAdaptor(name: String) extends LibraryAdaptor(name) {

  override def initialize() { /* nop */ }

  override def parseOnce(json: String) = {
      time(parse(json /* , classOf[Tweet] */) ) // TODO: add map (JsonSmart v.2)
  }

  def mapTweet(json: String) = 0L

  override def hasMap = false

  override def hasUnparse: Boolean = true

  override def unparseOnce(json: String) = {
    val x = parse(json)
    time(JSONValue.toJSONString(x))
  }

  override def hasUnmap: Boolean = false
}
