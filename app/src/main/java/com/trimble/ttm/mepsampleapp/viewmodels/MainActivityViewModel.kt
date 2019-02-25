package com.trimble.ttm.mepsampleapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trimble.ttm.backbone.api.BackboneKeys
import com.trimble.ttm.backbone.api.GpsData
import com.trimble.ttm.backbone.api.RxBackbone
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class MainActivityViewModel : ViewModel() {

    private val _gpsLocationSteam = MutableLiveData<GpsData>()
    private val _gpsLocationStreamInProgress = MutableLiveData<Boolean>()
    val gpsLocationStream: LiveData<GpsData> = _gpsLocationSteam
    val gpsLocationStreamInProgress: LiveData<Boolean> = _gpsLocationStreamInProgress

    private val _gpsLocationStreamDisposable = CompositeDisposable()

    init {
        _gpsLocationStreamInProgress.value = false
    }

    fun getGpsLocation(backbone: RxBackbone): GpsData {
        // If the result is null, then we have no GPS data. If you want to wait for data
        // use the asynchronous call.
        val result = backbone.fetch(BackboneKeys.GPS_DEGREES_KEY) ?: throw Exception("No GPS data available")
        return result.valueAs()
    }

    fun triggerGpsLocationStream(backbone: RxBackbone) {
        if (gpsLocationStreamInProgress.value!!) return
        _gpsLocationStreamInProgress.value = true
        _gpsLocationStreamDisposable.add(backbone.monitorFetch(BackboneKeys.GPS_DEGREES_KEY)
            .map { it.valueAs(GpsData::class.java) }
            .subscribeBy(onNext = { _gpsLocationSteam.value = it },
                onError = { _gpsLocationStreamInProgress.value = false },
                onComplete = { _gpsLocationStreamInProgress.value = false }
            )
        )
    }

    fun stopGpsLocationStream() {
        _gpsLocationStreamDisposable.clear()
        _gpsLocationStreamInProgress.value = false
    }
}