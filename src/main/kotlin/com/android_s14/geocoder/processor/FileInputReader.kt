package com.android_s14.geocoder.processor

import java.io.FileReader
import kotlin.system.exitProcess

class FileInputReader(private val inputFileName: String) : InputReader {

  override fun read(): MutableList<String> {
    try {
      return FileReader(inputFileName).buffered().readLines() as MutableList
    } catch(e: Exception) {
      println("Please provide a file with a list of addresses")
      exitProcess(1)
    }
  }

}