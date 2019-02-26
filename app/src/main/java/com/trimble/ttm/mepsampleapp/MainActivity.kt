package com.trimble.ttm.mepsampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anychart.AnyChart.box
import com.anychart.chart.common.dataentry.BoxDataEntry
import com.trimble.ttm.mepsampleapp.view.Trip
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val model by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model.ignition.observe(this, Observer {
            ignition.set(it)
        })
        
        speedometer.speedTo(50f)

        trip.set(Trip(3723, 42))

        latency_chart.setChart(box().apply {
            box(listOf(BoxDataEntry("Latency (ms)", 2000, 2120, 2300, 2430, 2500))).apply {
                normal().apply {
                    fill("#00e6e6")
                    medianStroke("white", 4, "10 0", "miter", "butt")
                }
                selected().fill("#00e6e6")
            }
            background().fill("#151616")
        })
    }
}
