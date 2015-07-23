package com.fourtysevendeg.jsonScalaPerftest

import com.fourtysevendeg.jsonScalaPerftest.argonaut.ArgonautAdaptor

import collection.immutable.{Set, HashMap}

case class Experiment(operation: String, exclude: Set[String], warmUpIterations: Int = 5) {

  val adaptorsToTest = Experiment.allAdaptors.filterNot(s => exclude.contains(s.getName.toLowerCase))

  val categories = Category
    .getFilesMatching("data", f => f.isDirectory)
    .map(d => new Category(d.getCanonicalPath))

  def measureParsing(iterations: Int): Map[String, Map[String, Long]] = {
    def run(iterations: Int, libraryAdaptors: Seq[LibraryAdaptor]): Map[String, Map[String, Long]] = {

      categories.map { category =>
        category.name ->
          libraryAdaptors.map { adaptor =>
            adaptor.getName ->
              category.measure(operation, adaptor, iterations, warmUpIterations)
          }.toMap
      }.toMap
    }
    println("Parsing measurement (%d warmup, %d iterations on %s)...".format(warmUpIterations, iterations, adaptorsToTest.map(_.getName).mkString(", ")))
    run(iterations, adaptorsToTest.toSeq)
  }

  def measureUnparsing(iterations: Int): Map[String, Map[String, Long]] = {
    def run(iterations: Int, libraryAdaptors: Seq[LibraryAdaptor]): Map[String, Map[String, Long]] = {

      categories.map { category =>
        category.name ->
          libraryAdaptors.map { adaptor =>
            adaptor.getName ->
              category.measure(operation, adaptor, iterations, warmUpIterations)
          }.toMap
      }.toMap
    }

    val targetAdaptors = adaptorsToTest.filter(_.hasUnparse)
    println("Unparsing measurement (%d warmup, %d iterations on %s)...".format(warmUpIterations, iterations, targetAdaptors.map(_.getName).mkString(", ")))
    run(iterations, targetAdaptors)
  }

  def measureMapping(iterations: Int): Map[String, Map[String, Long]] = {
    def run(iterations: Int, libraryAdaptors: Seq[LibraryAdaptor]): Map[String, Map[String, Long]] = {

      categories.map { category =>
        category.name ->
          libraryAdaptors.map { adaptor =>
            adaptor.getName ->
              category.measure(operation, adaptor, iterations, warmUpIterations)
          }.toMap
      }.toMap
    }

    val targetAdaptors = adaptorsToTest.filter(_.hasMap)
    println("Mapping measurement (%d warmup, %d iterations on %s)...".format(warmUpIterations, iterations, targetAdaptors.map(_.getName).mkString(", ")))
    run(iterations, targetAdaptors)
  }

}

object Experiment {

  val allAdaptors = Array(new liftjson.LiftJsonAdaptor("lift")
    , new jsonsmart.JsonSmartAdaptor("JsonSmart")
    , new spray.SprayAdaptor("spray")
    , new persist.PersistAdaptor("persist")
    , new rojoma.RojomaAdaptor("rojoma")
    , new twitter.TwitterAdaptor("twitter")
    , new scalalib.ScalaLibAdaptor("scalalib")
    , new jackson.JacksonAdaptor("jackson")
    , new play.PlayAdaptor("play")
    , new jawn.JawnAdaptor("jawn")
    , new ArgonautAdaptor("argonaut")
  )
}
