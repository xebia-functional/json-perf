package com.fourtysevendeg.jsonScalaPerftest.liftjson

import net.liftweb.json._
import com.fourtysevendeg.jsonScalaPerftest.LibraryAdaptor
import com.fourtysevendeg.jsonScalaPerftest.domain.{Tweet,Place}

/**
 * A helper that will JSON serialize BigDecimal
 */
object BigDecimalSerializer extends Serializer[BigDecimal] {
  private val Class = classOf[BigDecimal]

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), BigDecimal] = {
    case (TypeInfo(Class, _), json) => json match {
      case JInt(iv) => BigDecimal(iv)
      case JDouble(dv) => BigDecimal(dv)
      case value => throw new MappingException("Can't convert " + value + " to " + Class)
    }
  }

  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case d: BigDecimal => JDouble(d.doubleValue)
  }
}

class LiftJsonAdaptor(name: String) extends LibraryAdaptor(name) {

  override def initialize() {
    /* nop */
  }

  override def parseOnce(json: String) = {
    implicit val formats = DefaultFormats
    time {
      parse(json) match {
        case obj: JObject => obj
        case array: JArray => array
        case _ => JNull
      }
    }
  }

  override def mapTweet(json: String) = {
    time {
      implicit val formats = DefaultFormats
      parse(json) match {
        case obj: JObject =>
          List(obj.extract[Tweet])
        case array: JArray =>
          array.extract[List[Tweet]]
        case _ => List[Tweet]()
      }
    }
  }

  override def mapPlace(json: String) = {
    time {
      implicit val formats = DefaultFormats + BigDecimalSerializer
      parse(json) match {
        case obj: JObject =>
          List(obj.extract[Place])
        case array: JArray =>
          array.extract[List[Place]]
        case _ => List[Place]()
      }
    }
  }


  override def hasMap = true

  override def hasUnparse: Boolean = true

  override def unparseOnce(json: String) = {
    val x = parse(json)
    time(compactRender(x))
  }

  override def hasUnmap: Boolean = false
}
