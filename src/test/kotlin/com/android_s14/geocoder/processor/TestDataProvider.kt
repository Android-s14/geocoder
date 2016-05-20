package com.android_s14.geocoder.processor

import com.android_s14.geocoder.Coordinates
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever

val fakeInput: MutableList<String> get() = mutableListOf("test_address1", "test_address2")
val fakeCoordinates: Coordinates get() = Coordinates("lat", "long").apply { this.address = "address" }
val fakeException = RuntimeException("test exception")

fun getEmptyWriter(): OutputWriter {
  return object : OutputWriter {
    override fun write(lines: List<String>) {
    }
  }
}

fun getNonEmptyFetcher(): CoordinatesFetcher {
  return object : CoordinatesFetcher {
    override fun getCoordinates(address: String) = fakeCoordinates
  }
}

fun getNullFetcher(): CoordinatesFetcher {
  return object : CoordinatesFetcher {
    override fun getCoordinates(address: String): Coordinates? {
      return null
    }
  }
}

fun getExceptionFetcher(): CoordinatesFetcher {
  return object : CoordinatesFetcher {
    override fun getCoordinates(address: String) = throw fakeException
  }
}

fun getFirstTimeExceptionFetcher(): CoordinatesFetcher {
  return mock<CoordinatesFetcher>().apply {
    whenever(this.getCoordinates(any())).thenThrow(fakeException).thenReturn(fakeCoordinates)
  }
}

fun getNonEmptyReader(): InputReader {
  return object : InputReader {
    override fun read() = fakeInput
  }
}