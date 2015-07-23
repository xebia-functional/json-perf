package com.fourtysevendeg.jsonScalaPerftest.jackson

import com.fourtysevendeg.jsonScalaPerftest.LibraryAdaptor
import com.fasterxml.jackson.databind.{SerializationFeature, DeserializationFeature, ObjectMapper}
import com.fourtysevendeg.jsonScalaPerftest.domain.{Place, Tweet}

import com.fasterxml.jackson.module.scala.DefaultScalaModule

class JacksonAdaptor(name: String) extends LibraryAdaptor(name) {

  var m: ObjectMapper = _

  override def initialize() {
    m = new ObjectMapper
    m.registerModule(new DefaultScalaModule)
    m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    m.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
  }

  override def parseOnce(json: String) = {
    time(m.readTree(json))
  }

  override def mapTweet(json: String) = {
    time(m.readValue(json, classOf[Tweet]))
  }

  override def mapPlace(json: String) = {
    time(m.readValue(json, classOf[Place]))
  }

  override def hasMap = true

  override def hasUnparse: Boolean = true

  override def unparseOnce(json: String) = {
    val tree = m.readTree(json)
    time(m.writeValueAsString())
  }

  override def hasUnmap: Boolean = false
}
