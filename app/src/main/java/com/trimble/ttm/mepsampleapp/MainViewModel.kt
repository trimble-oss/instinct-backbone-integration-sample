package com.trimble.ttm.mepsampleapp

import android.app.Application
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import java.util.concurrent.TimeUnit.MINUTES
import java.util.concurrent.TimeUnit.SECONDS


class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val backbone = BackboneFactory.rxBackbone(getApplication())

    val ignition: LiveData<IgnitionState> =
        backbone.monitorFetch(listOf(IGNITION_KEY, ENGINE_ON_KEY))
            .map { result ->
                when {
                    result[ENGINE_ON_KEY]?.valueAs<Boolean>() == true -> IgnitionState.ENGINE_ON
                    result[IGNITION_KEY]?.valueAs<Boolean>() == true -> IgnitionState.ACCESSORY
                    else -> IgnitionState.OFF
                }
            }.toLiveData()

    val speed: LiveData<Float> =
        backbone.periodicFetch(2, SECONDS, ENGINE_SPEED_KMH_KEY)
            .map { it.valueAs<Float>() }
            .toLiveData()

    val trip: LiveData<Trip> =
        backbone.periodicFetch(1, MINUTES, listOf(ENGINE_ODOMETER_KM_KEY, TIME_ENGINE_ON_SECONDS_KEY))
            .mapToTrip()
            .toLiveData()

    val latency: LiveData<BoxData> = backbone.monitorFetch(GPS_DEGREES_KEY)
        .scan(Latency(1000)) { latency, _ -> latency.apply { add(SystemClock.uptimeMillis()) } }
        .sample(1, MINUTES)
        .map { latency -> latency.data }
        .toLiveData()


    private fun <T> Observable<T>.toLiveData(): LiveData<T> =
        androidx.lifecycle.LiveDataReactiveStreams.fromPublisher(toFlowable(BackpressureStrategy.ERROR))
}