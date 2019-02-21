package com.trimble.ttm.mepsampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

enum class IGNITION_STATE {
    OFF,
    ACCESSORY,
    ENGINE_ON,
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speedometer.speedTo(50f)

        gear.currentProgress = 3
        setIgnintionTo(IGNITION_STATE.ACCESSORY)
    }

    private fun setIgnintionTo(state: IGNITION_STATE) {
        ignition_off.setImageResource(R.drawable.off_circle)
        accessory.setImageResource(R.drawable.off_circle)
        engine_on.setImageResource(R.drawable.off_circle)
        when (state) {
            IGNITION_STATE.OFF -> ignition_off.setImageResource(R.drawable.ignition_off_circle)
            IGNITION_STATE.ACCESSORY -> accessory.setImageResource(R.drawable.ignition_accessory_circle)
            IGNITION_STATE.ENGINE_ON -> engine_on.setImageResource(R.drawable.engine_on_circle)
        }
    }
}
