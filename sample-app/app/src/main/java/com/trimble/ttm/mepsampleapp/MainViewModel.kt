/*
 * © 2022 Trimble Inc. Used under license. All rights reserved. Unauthorized duplication, copying or use prohibited.
 */

package com.trimble.ttm.mepsampleapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.trimble.ttm.backbone.api.BackboneFactory
import com.trimble.ttm.backbone.api.data.*
import com.trimble.ttm.backbone.api.data.eld.CurrentUser
import com.trimble.ttm.backbone.api.data.user.UserName
import com.trimble.ttm.mepsampleapp.view.BoxData
import com.trimble.ttm.mepsampleapp.view.IgnitionState
import com.trimble.ttm.mepsampleapp.view.Trip
import java.util.concurrent.TimeUnit.MINUTES
import java.util.concurrent.TimeUnit.SECONDS

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val backbone = BackboneFactory.backbone(getApplication())

    private val _ignition = MutableLiveData<IgnitionState>()
    private val _speed = MutableLiveData<Float>()
    private val _trip = MutableLiveData<Trip>()
    private val _customerId = MutableLiveData<CustomerId>()
    private val _userName = MutableLiveData<UserName>()
    private val _latency = MutableLiveData<BoxData>()

    val ignition: LiveData<IgnitionState> = _ignition
    val speed: LiveData<Float> = _speed
    val trip: LiveData<Trip> = _trip
    val latency: LiveData<BoxData> = _latency
    val customerId: LiveData<CustomerId> = _customerId
    val userName: LiveData<UserName> = _userName

    private val userNameStoppable = backbone
        .monitorChangesInDataFor(UserName)
        .alsoMonitor(CurrentUser)
        .handle { result ->
            result[CurrentUser]?.data?.let {
                result[UserName]?.data?.get(it)?.let {
                    _userName.postValue(it)
                }
            }
        }

    private val companyIdStoppable = backbone
        .monitorChangesInDataFor(CustomerId)
        .handle {
            it.data?.let {
                _customerId.postValue(it)
            }
        }

    private val ignitionStoppable = backbone
        .monitorChangesInDataFor(Ignition)
        .alsoMonitor(EngineOn)
        .handle { result ->
            _ignition.postValue(
                when {
                    result[EngineOn]?.data?.value == true -> IgnitionState.ENGINE_ON
                    result[Ignition]?.data?.value == true -> IgnitionState.ACCESSORY
                    else -> IgnitionState.OFF
                }
            )
        }

    private val speedStoppable = backbone
        .retrieveDataFor(EngineSpeedKmh)
        .every(2, SECONDS)
        .handle { (speed, _) -> _speed.postValue(speed?.value?.toFloat() ?: 0f) }

    private val tripStoppable = TripUpdater().let { updateTrip ->
        backbone
            .retrieveDataFor(EngineOdometerKm)
            .alsoRetrieve(TimeEngineOn)
            .every(1, MINUTES)
            .handle { result ->
                result[TimeEngineOn]?.let { (engineOn, _) ->
                    engineOn?.let { timeEngineOn ->
                        _trip.postValue(
                            updateTrip.with(
                                odometer = result[EngineOdometerKm]?.data?.value?.toInt() ?: 0,
                                timeEngineOn = timeEngineOn.value
                            )
                        )
                    }
                }
            }
    }

    private val latencyStoppable =
        LatencyCalculator(maxWindowSize = 1000).let { latencyCalculator ->
            backbone
                .monitorChangesInDataFor(GpsDegrees)
                .handle { (gps, receivedTime) ->
                    gps?.run {
                        val latencySeconds = (receivedTime.time - sentTime.time) / 1000f
                        latencyCalculator.add(latencySeconds)
                        _latency.postValue(latencyCalculator.data)
                    }
                }
        }

    override fun onCleared() {
        super.onCleared()

        ignitionStoppable.stop()
        speedStoppable.stop()
        tripStoppable.stop()
        latencyStoppable.stop()
        companyIdStoppable.stop()
        userNameStoppable.stop()
    }
}