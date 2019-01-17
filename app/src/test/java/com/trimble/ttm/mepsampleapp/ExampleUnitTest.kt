package com.trimble.ttm.mepsampleapp

import com.google.gson.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.trimble.ttm.backbone.api.Backbone
import com.trimble.ttm.backbone.api.BackboneData
import com.trimble.ttm.backbone.api.BackboneKeys
import com.trimble.ttm.backbone.api.GpsData
import com.trimble.ttm.mepsampleapp.viewmodels.MainActivityViewModel
import org.junit.Assert
import org.junit.Test
import java.lang.reflect.Type
import java.util.*


class MainActivityViewModelUnitTest {
    @Test
    fun gpsData_singleInstanceIsCorrect() {

        val gpsData = GpsData(1.0, 10.0, 0.0, Date(1122345*1000))

        // You have to use GSON if you are going to use a Trimble data object. you must also use
        // the custom date serializer if the object uses a date field.
        val gson = GsonBuilder().registerTypeAdapter(Date::class.java, DateSerializer()).create()
        val json = gson.toJson(gpsData)

        val backbone : Backbone = mock()
        val mainActivityViewModel = MainActivityViewModel()
        whenever(backbone.fetch(listOf(BackboneKeys.GPS_DEGREES_KEY)))
            .thenReturn(mapOf("" to BackboneData(json.toString(), Date(123456), Date(123456))))
        val gps = mainActivityViewModel.getGpsLocation(backbone)
        Assert.assertEquals(gpsData, gps)
    }
}

private class DateSerializer : JsonSerializer<Date> {
    override fun serialize(src: Date?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonParser().parse("${src!!.time/1000L}")
    }
}