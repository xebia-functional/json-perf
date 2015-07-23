package com.fourtysevendeg.jsonScalaPerftest.output


class ConsoleReporter(operation:String, ms: Map[String, Map[String, Long]]) {

  def printResults() {
    for ((dataset, info) <- ms) {
      for ((adaptor, measurement) <- info) {
        val v = measurement.toDouble / 1000000.0      // nano -> milli
        print(f"($operation,$adaptor,$dataset) = $v%.3f ms\n")
      }
    }
  }
}
