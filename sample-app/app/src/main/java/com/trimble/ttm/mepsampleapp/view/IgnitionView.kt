/*
 * © 2022 Trimble Inc. Used under license. All rights reserved. Unauthorized duplication, copying or use prohibited.
 */

package com.trimble.ttm.mepsampleapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.trimble.ttm.mepsampleapp.R

enum class IgnitionState {
    OFF,
    ACCESSORY,
    ENGINE_ON,
}

class IgnitionView(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    private var ignitionOff: ImageView
    private var accessory: ImageView
    private var engineOn: ImageView

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.IgnitionView, 0, 0).apply {
            val layout = getBoolean(R.styleable.IgnitionView_vertical, true).let { vertical ->
                if (vertical) R.layout.ignition_vertical_layout
                else R.layout.ignition_horizontal_layout
            }
            View.inflate(context, layout, this@IgnitionView).apply {
                ignitionOff = this.findViewById(R.id.ignition_off)
                accessory = this.findViewById(R.id.accessory)
                engineOn = this.findViewById(R.id.engine_on)

            }
        }
        set(IgnitionState.OFF)
    }

    fun set(state: IgnitionState) {
        ignitionOff.setImageResource(R.drawable.off_circle)
        accessory.setImageResource(R.drawable.off_circle)
        engineOn.setImageResource(R.drawable.off_circle)
        when (state) {
            IgnitionState.OFF -> ignitionOff.setImageResource(R.drawable.ignition_off_circle)
            IgnitionState.ACCESSORY -> accessory.setImageResource(R.drawable.ignition_accessory_circle)
            IgnitionState.ENGINE_ON -> engineOn.setImageResource(R.drawable.engine_on_circle)
        }
        invalidate()
        requestLayout()
    }
}