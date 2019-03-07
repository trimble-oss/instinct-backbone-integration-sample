package com.trimble.ttm.mepsampleapp

import com.trimble.ttm.backbone.api.BackboneData
import com.trimble.ttm.backbone.api.BackboneKeys.ENGINE_ODOMETER_KM_KEY
import com.trimble.ttm.backbone.api.BackboneKeys.TIME_ENGINE_ON_SECONDS_KEY
import com.trimble.ttm.backbone.api.DATA_KEY
import com.trimble.ttm.backbone.api.MESSAGE_SENT_TIME_KEY
import com.trimble.ttm.mepsampleapp.view.Trip
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test
import java.util.*

class TripUpdaterTest {
    private lateinit var tripUpdater: TripUpdater

    @Before
    fun before() {
        tripUpdater = TripUpdater()
    }

    @Test
    fun `should return null when time engine on not available`() {
        val trip = tripUpdater.with(mapOf(ENGINE_ODOMETER_KM_KEY to odometer(4242.42)))

        assertNull(trip)
    }

    @Test
    fun `should return Trip after first result`() {
        val trip = tripUpdater.with(
            mapOf(
                ENGINE_ODOMETER_KM_KEY to odometer(4242.42),
                TIME_ENGINE_ON_SECONDS_KEY to timeEngineOn(2)
            )
        )

        assertEquals(Trip(2, 0), trip)
    }

    @Test
    fun `should return correct Trip after multiple results`() {
        tripUpdater.with(
            mapOf(
                ENGINE_ODOMETER_KM_KEY to odometer(4242.42),
                TIME_ENGINE_ON_SECONDS_KEY to timeEngineOn(2)
            )
        )

        val trip = tripUpdater.with(
            mapOf(
                ENGINE_ODOMETER_KM_KEY to odometer(4243.0),
                TIME_ENGINE_ON_SECONDS_KEY to timeEngineOn(8)
            )
        )

        assertEquals(Trip(8, 1), trip)
    }

    @Test
    fun `should return Trip with zero distance when odometer not available`() {
        val trip = tripUpdater.with(
            mapOf(
                TIME_ENGINE_ON_SECONDS_KEY to timeEngineOn(2)
            )
        )

        assertEquals(Trip(2, 0), trip)
    }

    private fun odometer(reading: Double) =
        BackboneData("{$DATA_KEY:$reading, $MESSAGE_SENT_TIME_KEY: 1234567890}", Date())

    private fun timeEngineOn(reading: Long) =
        BackboneData("{$DATA_KEY:$reading, $MESSAGE_SENT_TIME_KEY: 1234567890}", Date())
}