/*
 * © 2019 Trimble Inc. Used under license. All rights reserved. Unauthorized duplication, copying or use prohibited.
 */

package com.trimble.ttm.mepsampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val model: MainViewModel by lazy { MainViewModel(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model.ignition.observe(this, {
            ignition.set(it)
        })

        model.speed.observe(this, {
            speedometer.speedTo(it)
        })

        model.trip.observe(this, {
            trip.set(it)
        })

        model.latency.observe(this, {
            latency_chart.set(it)
        })
    }
}
