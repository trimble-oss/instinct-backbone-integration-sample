package com.trimble.ttm.mepsampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.trimble.ttm.mepsampleapp.view.BoxData
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

        model.speed.observe(this, Observer {
            speedometer.speedTo(it)
        })

        model.trip.observe(this, Observer {
            trip.set(it)
        })

        latency_chart.set(BoxData(2F, 2.1F, 2.4F, 3.8F, 4.8F))
    }
}
