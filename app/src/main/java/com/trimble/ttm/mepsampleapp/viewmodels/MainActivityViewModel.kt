package com.trimble.ttm.mepsampleapp.viewmodels

import androidx.lifecycle.ViewModel
import com.trimble.ttm.backbone.api.Backbone
import com.trimble.ttm.backbone.api.BackboneKeys
import com.trimble.ttm.backbone.api.GpsData

class MainActivityViewModel: ViewModel() {

    fun getGpsLocation(backbone: Backbone): GpsData {
        val result = backbone.fetch(listOf(BackboneKeys.GPS_DEGREES_KEY))
        // If we list is empty we have not GPS data. If you want to wait for data
        // use the asynchronous call.
        if(result.values.isEmpty())
            throw Exception("No GPS data available")
        val x = result.values.first()
        return x.valueAs()
    }
}