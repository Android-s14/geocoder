package com.android_s14.geocoder.processor

import com.android_s14.geocoder.Coordinates

interface CoordinatesFetcher {
  fun getCoordinates(address: String): Coordinates?
}

interface InputReader {
  fun read(): MutableList<String>
}

interface OutputWriter {
  fun write(lines: List<String>): Unit
}