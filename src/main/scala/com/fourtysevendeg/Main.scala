package com.fourtysevendeg.jsonScalaPerftest

import joptsimple._
import scala.collection.JavaConversions._
import scala.collection.immutable.HashSet
import output.{ConsoleReporter, ChartReporter}

object Main extends App {
  val argParser = new OptionParser()
  val listOpt = argParser.accepts("list", "List known JSON libraries.")
  val iterationsOpt = argParser
    .accepts("iterations", "Number of iterations for measurements.")
    .withRequiredArg()
    .describedAs("iterations")
    .ofType(classOf[Int])
    .defaultsTo(200)
  val warmUpOpt = argParser
    .accepts("warmup", "Number of iterations for warm up.")
    .withRequiredArg()
    .describedAs("warmup")
    .ofType(classOf[Int])
    .defaultsTo(25)
  val excludeOpt = argParser
    .accepts("exclude", "Comma-separated list of library names to exclude from run, e.g., twitter,scalalib.")
    .withRequiredArg()
    .withValuesSeparatedBy(',')
    .describedAs("exclude")
    .ofType(classOf[String])
  val operationOpt = argParser
    .accepts("operation", "Test, p=parse,up=unparse,m=map,um=unmap")
    .withRequiredArg()
    .describedAs("Character; default is 'c'")
    .ofType(classOf[String])
    .defaultsTo("c")
  val reportOpt = argParser
    .accepts("report", "Result generation, c=console, b=bar chart.")
    .withRequiredArg()
    .describedAs("Character; default is 'c'")
    .ofType(classOf[String])
    .defaultsTo("c")
  val chartTitleOpt = argParser
    .accepts("title", "Chart title.")
    .withRequiredArg()
    .describedAs("title")
    .ofType(classOf[String])
    .defaultsTo("Operation:")

  val options = try {
    argParser.parse(args: _*)
  } catch {
    case e: OptionException => {
      println("Exception parsing command-line arguments: %s".format(e.getMessage))
      sys.exit(1)
    }
  }
  if (options.has(listOpt)) {
    println("Known JSON libraries:")
    Experiment.allAdaptors.foreach(a => println("%s, object mapping %s implemented".format(
      a.getName,
      if (a.hasMap) "IS" else "IS NOT")))
    sys.exit()
  }

  val iterations = options.valueOf[Int](iterationsOpt)
  val operation = if (options.has(operationOpt)) {
    options.valueOf(operationOpt) match {
      case "p" => "parse"
      case "up" => "unparse"
      case "m" => "map"
      case "um" => "unmap"
      case x: String => "parse"
    }
  } else {
    "parse"
  }
  val warmUpIterations = options.valueOf[Int](warmUpOpt)
  val exclude = if (options.has(excludeOpt)) options.valuesOf[String](excludeOpt).map(_.toLowerCase).toSet else HashSet[String]()

  println(s"Running $iterations iterations ($operation)")
  val experiment = Experiment(operation, exclude, warmUpIterations)
  val ms = operation match {
    case "parse" => experiment.measureParsing(iterations)
    case "map" => experiment.measureMapping(iterations)
    case "unparse" => experiment.measureUnparsing(iterations)
    case "unmap" => throw new Exception("UNMAP NYI")
  }

  if (options.has(reportOpt) && options.valueOf[String](reportOpt) == "b") {
    experiment.categories.map {
      case category =>
         val name = category.name
        val chart = new ChartReporter(operation, options.valueOf(chartTitleOpt), name, ms)
        chart.view
    }
  } else {
    val reporter = new ConsoleReporter(operation, ms)
    reporter.printResults()
  }
}

