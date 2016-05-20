package com.android_s14.geocoder.processor

import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

private const val OUTPUT_FILE_NAME = "coordinates.csv"

class FileOutputWriter : OutputWriter {

  override fun write(lines: List<String>) {
    Files.write(Paths.get(OUTPUT_FILE_NAME), lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND, StandardOpenOption.CREATE)
  }

}

