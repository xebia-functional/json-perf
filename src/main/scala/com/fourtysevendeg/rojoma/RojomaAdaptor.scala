package com.fourtysevendeg.jsonScalaPerftest.rojoma

//import com.rojoma.json.v3.ast.JValue
//import com.rojoma.json.v3.codec.DecodeError
import com.rojoma.json.v3.io.{CompactJsonWriter, JsonReader}
import com.rojoma.json.v3.util.{AutomaticJsonCodecBuilder, JsonUtil, SimpleJsonCodecBuilder}
import com.fourtysevendeg.jsonScalaPerftest.LibraryAdaptor

case class Url(indices: Seq[Int], url: String)

object Url {
  implicit val jCodec = AutomaticJsonCodecBuilder[Url]
}

case class Hashtag(indices: Seq[Int], text: String)

object Hashtag {
  implicit val jCodec = AutomaticJsonCodecBuilder[Hashtag]
}

case class UserMention(indices: Seq[Int], name: String)

object UserMention {
  implicit val jCodec1 = AutomaticJsonCodecBuilder[UserMention]
}

case class Entities(hashtags: Seq[Hashtag], urls: Seq[Url], userMentions: Seq[UserMention])

object Entities {
  implicit val jCodec = SimpleJsonCodecBuilder[Entities].build(
    "hashtags", _.hashtags,
    "urls", _.urls,
    "user_mentions", _.userMentions
  )}

case class Tweet(idStr: String, text: String, entities: Entities)

object Tweet {
  implicit val jCodec = SimpleJsonCodecBuilder[Tweet].build(
    "id_str", _.idStr,
    "text", _.text,
    "entities", _.entities
  )
}


case class Aspect(rating: Int, `type`: String)

object Aspect {

}

case class Review(aspects: Seq[Aspect], author_name: String, author_url: Option[String], text: String, time: Long)

object Review {

}

case class Photo(height: Int, width: Int, html_attributions: Seq[String], photo_reference: String)

object Photo {

}

case class When(day: Int, time: String)

object When {

}

case class Period(open: When, close: When)

object Period {

}

case class OpeningHours(open_now: Boolean, periods: Option[Seq[Period]])

object OpeningHours {

}

case class Location(lat: BigDecimal, lng: BigDecimal)

object Location {

}

case class Geometry(location: Location)

object Geometry {

}

case class AddressComponent(long_name: String, short_name: String, types: Seq[String])

object AddressComponent {

}

case class Place( address_components: Seq[AddressComponent]
                  , formatted_address: Option[String]
                  , formatted_phone_number: Option[String]
                  , geometry: Geometry
                  , icon: Option[String]
                  , id: String
                  , name: String
                  , international_phone_number: Option[String]
                  , opening_hours: Option[OpeningHours]
                  , photos: Option[Seq[Photo]]
                  , reference: String
                  , reviews: Option[Seq[Review]]
                  )

object Place {

}



class RojomaAdaptor(name: String) extends LibraryAdaptor(name) {

  def initialize() {}

  def mapTweet(json: String) = {
    time {
      JsonUtil.parseJson[Tweet](json) match {
        case Right(x) => x
        case Left(x) => throw new Exception("Rojoma mapTweet failed")
      }
    }
  }

  def parseOnce(json: String) = {
    time(JsonReader.fromString(json))
  }

  override def hasMap = true

  override def hasUnparse: Boolean = true

  override def unparseOnce(json: String) = {
    val x = JsonReader.fromString(json)
    time(CompactJsonWriter.toString(x))
  }

  override def hasUnmap: Boolean = false
}
