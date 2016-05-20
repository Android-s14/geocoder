package com.android_s14.geocoder

import com.android_s14.geocoder.processor.FileInputReader
import com.android_s14.geocoder.processor.FileOutputWriter
import com.android_s14.geocoder.processor.Processor

fun main(args: Array<String>?) {

  if (args == null || args.isEmpty()) {
    println("Please specify input file name")
    return
  }
  Processor(FileInputReader(args[0]), Service, FileOutputWriter()).process()

}