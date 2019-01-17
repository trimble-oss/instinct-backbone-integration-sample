package com.trimble.ttm.mepsampleapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.trimble.ttm.backbone.api.BackboneFactory
import com.trimble.ttm.backbone.api.GpsData
import com.trimble.ttm.mepsampleapp.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var _latitudeView: TextView
    private lateinit var _longitudeView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _latitudeView = findViewById(R.id.latitude_value)
        _longitudeView = findViewById(R.id.longitude_value)

        val backbone = BackboneFactory.backbone(applicationContext)
        val model = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        findViewById<Button>(R.id.get_gps_data).setOnClickListener {
            val gpsData = model.getGpsLocation(backbone)
            populateGpsData(gpsData)
        }
    }

    private fun populateGpsData(gpsData: GpsData) {
        _latitudeView.text = gpsData.latitudeInDegrees.toString()
        _longitudeView.text = gpsData.longitudeInDegrees.toString()
    }
}
