package com.fourtysevendeg.jsonScalaPerftest.play

import com.fourtysevendeg.jsonScalaPerftest.LibraryAdaptor
import play.api.libs.json._
import com.fourtysevendeg.jsonScalaPerftest.domain._

class PlayAdaptor(name: String) extends LibraryAdaptor(name) {

  implicit val urlReads = Json.reads[Url]
  implicit val hashTagReads = Json.reads[HashTag]
  implicit val userMentionReads = Json.reads[UserMention]
  implicit val entitiesReads = Json.reads[Entities]
  implicit val tweetReads = Json.reads[Tweet]

  implicit val addressComponentReads = Json.reads[AddressComponent]
  implicit val aspectReads = Json.reads[Aspect]
  implicit val locationReads = Json.reads[Location]
  implicit val geometryReads = Json.reads[Geometry]
  implicit val whenReads = Json.reads[When]
  implicit val periodReads = Json.reads[Period]
  implicit val openingHoursReads = Json.reads[OpeningHours]
  implicit val photoReads = Json.reads[Photo]
  implicit val reviewReads = Json.reads[Review]
  implicit val placeReads = Json.reads[Place]

  def hasMap = true

  def initialize() {}

  def parseOnce(json: String) = {
    time(Json.parse(json))
  }

  def mapTweet(json: String) = {
    time {
      val parsed = Json.parse(json)
      parsed.as[Tweet]
    }
  }

  override def mapPlace(json: String) = {
    time {
      val parsed = Json.parse(json)
      parsed.as[Place]
    }
  }

  override def hasUnparse: Boolean = true

  override def unparseOnce(json: String) = {
    val x = Json.parse(json)
    time(Json.stringify(x))
  }

  override def hasUnmap: Boolean = false
}
