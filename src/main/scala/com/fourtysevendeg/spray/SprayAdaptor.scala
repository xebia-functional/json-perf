package com.fourtysevendeg.jsonScalaPerftest.spray

import com.fourtysevendeg.jsonScalaPerftest.LibraryAdaptor
import spray.json._
import com.fourtysevendeg.jsonScalaPerftest.domain._

object myJsonProtocol extends DefaultJsonProtocol {

  implicit val urlFormat = jsonFormat2(Url)
  implicit val hashtagFormat = jsonFormat2(HashTag)
  implicit val userMentionFormat = jsonFormat2(UserMention)
  implicit val entitiesFormat = jsonFormat3(Entities)
  implicit val tweetFormat = jsonFormat3(Tweet)

  implicit val aspectFormat = jsonFormat2(Aspect)
  implicit val reviewFormat = jsonFormat5(Review)
  implicit val photoFormat = jsonFormat4(Photo)
  implicit val whenFormat = jsonFormat2(When)
  implicit val periodFormat = jsonFormat2(Period)
  implicit val openingHoursFormat = jsonFormat2(OpeningHours)
  implicit val locationFormat = jsonFormat2(Location)
  implicit val geometryFormat = jsonFormat1(Geometry)
  implicit val addressComponentFormat = jsonFormat3(AddressComponent)
  implicit val placeFormat = jsonFormat12(Place)
}

class SprayAdaptor(name: String) extends LibraryAdaptor(name) {

  override def initialize() {
    /* nop */
  }

  override def parseOnce(json: String) = {
    time(json.parseJson)
  }

  override def mapTweet(json: String) = {
    time {
      import myJsonProtocol._
      json.parseJson.convertTo[Tweet]
    }
  }

  override def mapPlace(json: String) = {
    time {
      import myJsonProtocol._
      json.parseJson.convertTo[Place]
    }
  }


  override def hasMap = true

  override def hasUnparse: Boolean = true

  override def unparseOnce(json: String) = {
    val x = json.parseJson
    time(x.compactPrint)
  }

  override def hasUnmap: Boolean = false
}
