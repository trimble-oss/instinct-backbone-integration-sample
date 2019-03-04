package com.trimble.ttm.mepsampleapp

import com.trimble.ttm.backbone.api.BackboneKeys.ENGINE_ODOMETER_KM_KEY
import com.trimble.ttm.backbone.api.BackboneKeys.TIME_ENGINE_ON_SECONDS_KEY
import com.trimble.ttm.backbone.api.BackboneResult
import com.trimble.ttm.mepsampleapp.view.Trip
import io.reactivex.Maybe.empty
import io.reactivex.Maybe.just
import io.reactivex.Observable

fun Observable<BackboneResult>.mapToTrip(): Observable<Trip> {
    val updateTrip = UpdateTrip()

    return flatMapMaybe { result ->
        updateTrip.with(result)?.let { just(it) } ?: empty()
    }
}

private class UpdateTrip {
    private var distanceAtStart: Int = 0
    private var prevTimeOn: Long = Long.MAX_VALUE

    fun with(result: BackboneResult): Trip? {
        val odometer = result[ENGINE_ODOMETER_KM_KEY]?.valueAs<Int>() ?: 0

        return result[TIME_ENGINE_ON_SECONDS_KEY]?.valueAs<Long>()?.let { timeOn ->
            if (timeOn < prevTimeOn) distanceAtStart = odometer

            prevTimeOn = timeOn

            Trip(timeOn, odometer - distanceAtStart)
        }
    }
}