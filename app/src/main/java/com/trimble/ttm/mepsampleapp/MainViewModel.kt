package com.trimble.ttm.mepsampleapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.trimble.ttm.backbone.api.BackboneFactory
import com.trimble.ttm.backbone.api.BackboneKeys.ENGINE_ON_KEY
import com.trimble.ttm.backbone.api.BackboneKeys.IGNITION_KEY
import com.trimble.ttm.mepsampleapp.view.IgnitionState
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable


class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val backbone = BackboneFactory.rxBackbone(getApplication())

    val ignition: LiveData<IgnitionState> = backbone.monitorFetch(listOf(IGNITION_KEY, ENGINE_ON_KEY))
        .map { result ->
            when {
                result[ENGINE_ON_KEY]?.valueAs<Boolean>() == true -> IgnitionState.ENGINE_ON
                result[IGNITION_KEY]?.valueAs<Boolean>() == true -> IgnitionState.ACCESSORY
                else -> IgnitionState.OFF
            }
        }.toLiveData()

    private fun <T> Observable<T>.toLiveData(): LiveData<T> =
        androidx.lifecycle.LiveDataReactiveStreams.fromPublisher(toFlowable(BackpressureStrategy.ERROR))
}