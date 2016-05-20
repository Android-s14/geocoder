package com.android_s14.geocoder.processor

private const val LIMIT = 10
private const val ERROR_RETRY_LIMIT = 3

class Processor(inputReader: InputReader, private val coordinatesFetcher: CoordinatesFetcher, private val outputWriter: OutputWriter) {

  val inputLines = inputReader.read()
  val outputLines = mutableListOf<String>()
  val totalCount = inputLines.size
  var currentCount = 0

  fun process(): Unit {
    while (inputLines.size > 0) {
      val subList = inputLines.subList(0, inputLines.size.coerceIn(0, LIMIT)).toList()
      currentCount += subList.size
      println("Getting coordinates for $currentCount / $totalCount")
      subList.map { fetchCoordinates(it) }.apply { outputLines.addAll(this) }
      inputLines.removeAll(subList)
      if (inputLines.size > 0) Thread.sleep(1100)
    }
    outputWriter.write(outputLines)
  }

  private fun fetchCoordinates(address: String, currentRetryCount: Int = 0): String {
    try {
      return coordinatesFetcher.getCoordinates(address).toString()
    } catch(e: Exception) {
      return if (currentRetryCount < ERROR_RETRY_LIMIT) fetchCoordinates(address, currentRetryCount.inc()) else "error getting coordinates"
    }
  }

}