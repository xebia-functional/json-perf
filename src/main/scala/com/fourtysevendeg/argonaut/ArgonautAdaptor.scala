package com.fourtysevendeg.jsonScalaPerftest.argonaut

import com.fourtysevendeg.jsonScalaPerftest.LibraryAdaptor
import scalaz._
import Scalaz._
import argonaut._
import Argonaut._

class ArgonautAdaptor(name: String) extends LibraryAdaptor(name) {

  case class Url(indices: List[Int], url: String)

  implicit def UrlEncodeJson: EncodeJson[Url] =
    EncodeJson((u: Url) =>
      ("indices" := u.indices) ->:
        ("url" := u.url) ->:
        jEmptyObject)

  implicit def UrlDecodeJson: DecodeJson[Url] =
    DecodeJson(c => for {
      indices <- (c --\ "indices").as[List[Int]]
      url <- (c --\ "url").as[String]
    } yield Url(indices, url))

  case class HashTag(indices: List[Int], text: String)

  implicit def HashTabEncodeJson: EncodeJson[HashTag] =
    EncodeJson((h: HashTag) =>
      ("indices" := h.indices) ->:
        ("text" := h.text) ->:
        jEmptyObject)

  implicit def HashTabDecodeJson: DecodeJson[HashTag] =
    DecodeJson(c => for {
      indices <- (c --\ "indices").as[List[Int]]
      text <- (c --\ "test").as[String]
    } yield HashTag(indices, text))

  case class UserMention(indices: List[Int], name: String)

  implicit def UserMentionEncodeJson: EncodeJson[UserMention] =
    EncodeJson((um: UserMention) =>
      ("indices" := um.indices) ->:
        ("name" := um.name) ->:
        jEmptyObject)

  implicit def UserMentionDecodeJson: DecodeJson[UserMention] =
    DecodeJson(c => for {
      indices <- (c --\ "indices").as[List[Int]]
      name <- (c --\ "name").as[String]
    } yield UserMention(indices, name))

  case class Entities(hashtags: List[HashTag], urls: List[Url], user_mentions: List[UserMention])

  implicit def EntitiesEncodeJson: EncodeJson[Entities] =
    EncodeJson((e: Entities) =>
      ("hashtags" := e.hashtags) ->:
        ("urls" := e.urls) ->:
        ("user_mentions" := e.user_mentions) ->:
        jEmptyObject)

  implicit def EntitiesDecodeJson: DecodeJson[Entities] =
    DecodeJson(c => for {
      hashtags <- (c --\ "hashtags").as[List[HashTag]]
      urls <- (c --\ "urls").as[List[Url]]
      user_mentions <- (c --\ "user_mentions").as[List[UserMention]]
    } yield Entities(hashtags, urls, user_mentions))

  case class Tweet(id_str: String, text: String, entities: Entities)

  implicit def TweetEncodeJson: EncodeJson[Tweet] =
    EncodeJson((t: Tweet) =>
      ("id_str" := t.id_str) ->:
        ("test" := t.text) ->:
        ("entities" := t.entities) ->:
        jEmptyObject)

  implicit def TweetDecodeJson: DecodeJson[Tweet] =
    DecodeJson(c => for {
      id_str <- (c --\ "id_str").as[String]
      text <- (c --\ "text").as[String]
      entities <- (c --\ "entities").as[Entities]
    } yield Tweet(id_str, text, entities))

  case class Aspect(rating: Int, `type`: String)

  implicit def AspectDecodeJson: DecodeJson[Aspect] =
    DecodeJson(c => for {
      rating <- (c --\ "rating").as[Int]
      t <- (c --\ "type").as[String]
    } yield Aspect(rating, t))

  case class Review(aspects: List[Aspect], author_name: String, author_url: Option[String], text: String, time: Long)

  implicit def ReviewDecodeJson: DecodeJson[Review] =
    DecodeJson(c => for {
      aspects <- (c --\ "aspects").as[List[Aspect]]
      author_name <- (c --\ "author_name").as[String]
      author_url <- (c --\ "author_url").as[Option[String]]
      text <- (c --\ "text").as[String]
      time <- (c --\ "time").as[Long]
    } yield Review(aspects, author_name, author_url, text, time))

  case class Photo(height: Int, width: Int, html_attributions: List[String], photo_reference: String)

  implicit def PhotoDecodeJson: DecodeJson[Photo] =
    DecodeJson(c => for {
      height <- (c --\ "height").as[Int]
      width <- (c --\ "width").as[Int]
      html_attributions <- (c --\ "html_attributions").as[List[String]]
      photo_reference <- (c --\ "photo_reference").as[String]
    } yield Photo(height, width, html_attributions, photo_reference))

  case class When(day: Int, time: String)

  implicit def WhenDecodeJson: DecodeJson[When] =
    DecodeJson(c => for {
      day <- (c --\ "day").as[Int]
      time <- (c --\ "time").as[String]
    } yield When(day, time))

  case class Period(open: When, close: When)

  implicit def PeriodDecodeJson: DecodeJson[Period] =
    DecodeJson(c => for {
      open <- (c --\ "open").as[When]
      close <- (c --\ "close").as[When]
    } yield Period(open, close))

  case class OpeningHours(open_now: Boolean, periods: Option[List[Period]])

  implicit def OpeningHoursDecodeJson: DecodeJson[OpeningHours] =
    DecodeJson(c => for {
      open_now <- (c --\ "open_now").as[Boolean]
      periods <- (c --\ "periods").as[Option[List[Period]]]
    } yield OpeningHours(open_now, periods))

  case class Location(lat: Double, lng: Double)

  implicit def LocationDecodeJson: DecodeJson[Location] =
    DecodeJson(c => for {
      lat <- (c --\ "lat").as[Double]
      lng <- (c --\ "lng").as[Double]
    } yield Location(lat, lng))

  case class Geometry(location: Location)

  implicit def GeometryDecodeJson: DecodeJson[Geometry] =
    DecodeJson(c => for {
      location <- (c --\ "location").as[Location]
    } yield Geometry(location))

  case class AddressComponent(long_name: String, short_name: String, types: List[String])

  implicit def AddressComponentDecodeJson: DecodeJson[AddressComponent] =
    DecodeJson(c => for {
      long_name <- (c --\ "long_name").as[String]
      short_name <- (c --\ "short_name").as[String]
      types <- (c --\ "types").as[List[String]]
    } yield AddressComponent(long_name, short_name, types))

  case class Place(address_components: List[AddressComponent]
                   , formatted_address: Option[String]
                   , formatted_phone_number: Option[String]
                   , geometry: Geometry
                   , icon: Option[String]
                   , id: String
                   , name: String
                   , international_phone_number: Option[String]
                   , opening_hours: Option[OpeningHours]
                   , photos: Option[List[Photo]]
                   , reference: String
                   , reviews: Option[List[Review]]
                    )

  implicit def PlaceDecodeJson: DecodeJson[Place] =
    DecodeJson(c => for {
      address_components <- (c --\ "address_components").as[List[AddressComponent]]
      formatted_address <- (c --\ "formatted_address").as[Option[String]]
      formatted_phone_number <- (c --\ "formatted_phone_number").as[Option[String]]
      geometry <- (c --\ "geometry").as[Geometry]
      icon <- (c --\ "icon").as[Option[String]]
      id <- (c --\ "id").as[String]
      name <- (c --\ "name").as[String]
      international_phone_number <- (c --\ "international_phone_number").as[Option[String]]
      opening_hours <- (c --\ "opening_hours").as[Option[OpeningHours]]
      photos <- (c --\ "photos").as[Option[List[Photo]]]
      reference <- (c --\ "reference").as[String]
      reviews <- (c --\ "reviews").as[Option[List[Review]]]
    } yield Place(address_components, formatted_address, formatted_phone_number,
      geometry, icon, id, name, international_phone_number,
      opening_hours, photos, reference, reviews))

  override def hasMap: Boolean = true

  override def parseOnce(json: String) = {
    time(Parse.parse(json))
  }

  override def mapTweet(json: String): Long = {
    time {
      val tweet: Option[Tweet] = Parse.decodeOption[Tweet](json)
    }
  }

  override def mapPlace(json: String): Long = {
    time {
      val place: Option[Place] = Parse.decodeOption[Place](json)
    }
  }

  override def initialize(): Unit = {}

  override def hasUnparse: JsonBoolean = true

  override def unparseOnce(json: String) = {
    val x: \/[String, Json] = Parse.parse(json)
    time(x.getOrElse(null).nospaces)
  }

  override def hasUnmap: JsonBoolean = false
}
