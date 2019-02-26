package com.trimble.ttm.mepsampleapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.trimble.ttm.mepsampleapp.R
import kotlinx.android.synthetic.main.ignition_layout.view.*

enum class IgnitionState {
    OFF,
    ACCESSORY,
    ENGINE_ON,
}

class IgnitionView(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {
    init {
        View.inflate(context, R.layout.ignition_layout, this)
        set(IgnitionState.OFF)
    }

    fun set(state: IgnitionState) {
        ignition_off.setImageResource(R.drawable.off_circle)
        accessory.setImageResource(R.drawable.off_circle)
        engine_on.setImageResource(R.drawable.off_circle)
        when (state) {
            IgnitionState.OFF -> ignition_off.setImageResource(R.drawable.ignition_off_circle)
            IgnitionState.ACCESSORY -> accessory.setImageResource(R.drawable.ignition_accessory_circle)
            IgnitionState.ENGINE_ON -> engine_on.setImageResource(R.drawable.engine_on_circle)
        }
        invalidate()
        requestLayout()
    }
}