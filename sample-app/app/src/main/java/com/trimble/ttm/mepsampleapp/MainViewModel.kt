/*
 * © 2019 Trimble Inc. Used under license. All rights reserved. Unauthorized duplication, copying or use prohibited.
 */

package com.trimble.ttm.mepsampleapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.trimble.ttm.backbone.api.BackboneFactory
import com.trimble.ttm.backbone.api.BackboneKeys.ENGINE_ODOMETER_KM_KEY
import com.trimble.ttm.backbone.api.BackboneKeys.ENGINE_ON_KEY
import com.trimble.ttm.backbone.api.BackboneKeys.ENGINE_SPEED_KMH_KEY
import com.trimble.ttm.backbone.api.BackboneKeys.GPS_DEGREES_KEY
import com.trimble.ttm.backbone.api.BackboneKeys.IGNITION_KEY
import com.trimble.ttm.backbone.api.BackboneKeys.TIME_ENGINE_ON_SECONDS_KEY
import com.trimble.ttm.mepsampleapp.view.BoxData
import com.trimble.ttm.mepsampleapp.view.IgnitionState
import com.trimble.ttm.mepsampleapp.view.Trip
import io.reactivex.Observable


class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val backbone = BackboneFactory.callbackBackbone(getApplication())

    private val _ignition = MutableLiveData<IgnitionState>()
    private val _speed = MutableLiveData<Float>()
    private val _trip = MutableLiveData<Trip>()
    private val _latency = MutableLiveData<BoxData>()

    val ignition: LiveData<IgnitionState> = _ignition
    val speed: LiveData<Float> = _speed
    val trip: LiveData<Trip> = _trip
    val latency: LiveData<BoxData> = _latency

    private val ignitionQuery = backbone.monitorFetch(listOf(IGNITION_KEY, ENGINE_ON_KEY)) { result ->
        _ignition.postValue(
            when {
                result[ENGINE_ON_KEY]?.valueAs<Boolean>() == true -> IgnitionState.ENGINE_ON
                result[IGNITION_KEY]?.valueAs<Boolean>() == true -> IgnitionState.ACCESSORY
                else -> IgnitionState.OFF
            }
        )
    }

    private val speedQuery = backbone.periodicFetch(periodInMillis = 2 * 1000, key = ENGINE_SPEED_KMH_KEY) { result ->
        _speed.postValue(result.valueAs())
    }

    private val tripQuery = TripUpdater().let { updateTrip ->
        backbone.periodicFetch(
            periodInMillis = 60 * 1000,
            keys = listOf(ENGINE_ODOMETER_KM_KEY, TIME_ENGINE_ON_SECONDS_KEY)
        ) { result ->
            updateTrip.with(result)?.let { _trip.postValue(it) }
        }
    }

    private val latencyQuery = LatencyCalculator(maxWindowSize = 1000).let { latencyCalculator ->
        backbone.monitorFetch(GPS_DEGREES_KEY) { result ->
            val latencySeconds = (result.receivedTime.time - result.sentTime.time) / 1000f
            latencyCalculator.add(latencySeconds)
            _latency.postValue(latencyCalculator.data)
        }
    }

    override fun onCleared() {
        super.onCleared()

        ignitionQuery.stop()
        speedQuery.stop()
        tripQuery.stop()
        latencyQuery.stop()
    }
}