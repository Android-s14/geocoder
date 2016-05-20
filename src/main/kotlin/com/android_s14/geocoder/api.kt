package com.android_s14.geocoder

import com.android_s14.geocoder.processor.CoordinatesFetcher
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.reflect.Type

private val KEY by lazy { readApiKey() }
private const val BASE_URL = "https://maps.googleapis.com/maps/api/"
private const val GEO_CODE_URL = "geocode/json"
private const val DIVIDER = "; "

object Service : CoordinatesFetcher {
  private val gson = GsonBuilder().registerTypeAdapter(Coordinates::class.java, CoordinatesAdapter()).create()
  private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build()
  private val service = retrofit.create(Api::class.java)

  override fun getCoordinates(address: String) = service.toLatLong(address).execute().body().apply { this.address = address }
}

interface Api {

  @GET(GEO_CODE_URL)
  fun toLatLong(@Query("address") address: String, @Query("key") key: String = KEY): Call<Coordinates>

}

data class Coordinates(val latitude: String, val longitude: String) {
  lateinit var address: String
  override fun toString() = "$address$DIVIDER$latitude$DIVIDER$longitude"
}

private class CoordinatesAdapter : JsonDeserializer<Coordinates> {

  override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Coordinates? {
    val location = json?.asJsonObject?.getAsJsonArray("results")?.get(0)?.asJsonObject?.getAsJsonObject("geometry")?.getAsJsonObject("location")
    val latitude = location?.get("lat")?.asString ?: return null
    val longitude = location?.get("lng")?.asString ?: return null
    return Coordinates(latitude, longitude)
  }

}

private fun readApiKey(): String {
  return Service::class.java.getResourceAsStream("/api.key").bufferedReader().use { it.readText() }
}