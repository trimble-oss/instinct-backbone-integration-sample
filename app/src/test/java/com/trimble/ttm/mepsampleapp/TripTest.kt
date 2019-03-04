package com.trimble.ttm.mepsampleapp

import com.trimble.ttm.backbone.api.BackboneData
import com.trimble.ttm.backbone.api.BackboneKeys.ENGINE_ODOMETER_KM_KEY
import com.trimble.ttm.backbone.api.BackboneKeys.TIME_ENGINE_ON_SECONDS_KEY
import com.trimble.ttm.backbone.api.BackboneResult
import com.trimble.ttm.backbone.api.DATA_KEY
import com.trimble.ttm.backbone.api.MESSAGE_SENT_TIME_KEY
import com.trimble.ttm.mepsampleapp.view.Trip
import io.reactivex.Observable.just
import org.junit.Test
import java.util.*

class TripTest {
    @Test
    fun `should not emit Trip when time engine on not available`() {
        just<BackboneResult>(mapOf(ENGINE_ODOMETER_KM_KEY to odometer(4242.42)))
            .mapToTrip()
            .test()
            .assertNoValues()
            .assertNoErrors()
    }

    @Test
    fun `should emit correct Trip after first result`() {
        just<BackboneResult>(
            mapOf(
                ENGINE_ODOMETER_KM_KEY to odometer(4242.42),
                TIME_ENGINE_ON_SECONDS_KEY to timeEngineOn(2)
            )
        )
            .mapToTrip()
            .test()
            .assertValue(Trip(2, 0))
            .assertNoErrors()
    }

    @Test
    fun `should emit correct Trip after multiple results`() {
        just<BackboneResult>(
            mapOf(
                ENGINE_ODOMETER_KM_KEY to odometer(4242.42),
                TIME_ENGINE_ON_SECONDS_KEY to timeEngineOn(2)
            ),
            mapOf(
                ENGINE_ODOMETER_KM_KEY to odometer(4243.0),
                TIME_ENGINE_ON_SECONDS_KEY to timeEngineOn(8)
            )
        )
            .mapToTrip()
            .skip(1)
            .test()
            .assertValue(Trip(8, 1))
            .assertNoErrors()
    }

    @Test
    fun `should emit Trip with zero distance when odometer not available`() {
        just<BackboneResult>(
            mapOf(
                TIME_ENGINE_ON_SECONDS_KEY to timeEngineOn(2)
            )
        )
            .mapToTrip()
            .test()
            .assertValue(Trip(2, 0))
            .assertNoErrors()
    }

    private fun odometer(reading: Double) =
        BackboneData("{$DATA_KEY:$reading, $MESSAGE_SENT_TIME_KEY: 1234567890}", Date())

    private fun timeEngineOn(reading: Long) =
        BackboneData("{$DATA_KEY:$reading, $MESSAGE_SENT_TIME_KEY: 1234567890}", Date())
}