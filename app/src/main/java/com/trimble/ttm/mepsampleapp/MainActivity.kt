package com.trimble.ttm.mepsampleapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.trimble.ttm.backbone.api.BackboneFactory
import com.trimble.ttm.backbone.api.GpsData
import com.trimble.ttm.mepsampleapp.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var _latitudeView: TextView
    private lateinit var _longitudeView: TextView

    private lateinit var _latitudeStreamView: TextView
    private lateinit var _longitudeStreamView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _latitudeView = findViewById(R.id.latitude_value)
        _longitudeView = findViewById(R.id.longitude_value)
        _latitudeStreamView = findViewById(R.id.latitude_stream_value)
        _longitudeStreamView = findViewById(R.id.longitude_stream_value)

        val backbone = BackboneFactory.rxBackbone(applicationContext)
        val model = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        findViewById<Button>(R.id.get_gps_data).setOnClickListener {
            val gpsData = model.getGpsLocation(backbone)
            populateGpsData(gpsData)
        }
        val triggerGpsStreamButton = findViewById<Button>(R.id.get_gps_data_stream)
        triggerGpsStreamButton.setOnClickListener{
            if(model.gpsLocationStreamInProgress.value!!)
                model.stopGpsLocationStream()
            else
                model.triggerGpsLocationStream(backbone)
        }

        model.gpsLocationStream.observe(this, gpsObserver)
        model.gpsLocationStreamInProgress.observe(this, Observer{
            if(it) triggerGpsStreamButton.text = getString(R.string.stop_gps_stream)
            else triggerGpsStreamButton.text = getString(R.string.start_gps_stream)
        })
    }

    private fun populateGpsData(gpsData: GpsData) {
        _latitudeView.text = gpsData.latitudeInDegrees.toString()
        _longitudeView.text = gpsData.longitudeInDegrees.toString()
    }

    private val gpsObserver = Observer<GpsData> { gpsData ->
        _latitudeStreamView.text = gpsData.latitudeInDegrees.toString()
        _longitudeStreamView.text = gpsData.longitudeInDegrees.toString()
    }
}
