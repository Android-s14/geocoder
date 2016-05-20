package com.android_s14.geocoder.processor

import com.nhaarman.mockito_kotlin.*
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PrepareEverythingForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareEverythingForTest()
class ProcessorTest {

  @Test
  fun testSanity() {
    val inputReader = getNonEmptyReader()
    val fetcher = getNonEmptyFetcher()
    val writer = getEmptyWriter()
    Processor(inputReader, fetcher, writer).process()
  }

  @Test
  fun testNullCoordinates() {
    Processor(getNonEmptyReader(), getNullFetcher(), getEmptyWriter()).process()
  }

  @Test
  fun testExceptionWhileFetching() {
    val writer = mock<OutputWriter>()
    Processor(getNonEmptyReader(), getExceptionFetcher(), writer).process()
    verify(writer).write(argThat { contains("error getting coordinates") })
    verifyNoMoreInteractions(writer)
  }

  @Test
  fun testFlakyExceptionWhileFetching() {
    val writer = mock<OutputWriter>()
    Processor(getNonEmptyReader(), getFirstTimeExceptionFetcher(), writer).process()
    verify(writer).write(argThat { contains(fakeCoordinates.toString()) })
    verifyNoMoreInteractions(writer)
  }

}