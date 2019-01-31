package com.trimble.ttm.mepsampleapp.viewmodels

import androidx.lifecycle.ViewModel
import com.trimble.ttm.backbone.api.Backbone
import com.trimble.ttm.backbone.api.BackboneKeys
import com.trimble.ttm.backbone.api.GpsData

class MainActivityViewModel : ViewModel() {

    fun getGpsLocation(backbone: Backbone): GpsData {
        val result = backbone.fetch(BackboneKeys.GPS_DEGREES_KEY) ?: throw Exception("No GPS data available")
        // If the result is null, then we have no GPS data. If you want to wait for data
        // use the asynchronous call.

        return result.valueAs()
    }
}