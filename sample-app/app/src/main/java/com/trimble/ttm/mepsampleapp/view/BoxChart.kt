/*
 * © 2019 Trimble Inc. Used under license. All rights reserved. Unauthorized duplication, copying or use prohibited.
 */

package com.trimble.ttm.mepsampleapp.view

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.annotation.ColorInt
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.trimble.ttm.mepsampleapp.R

data class BoxData(val min: Float, val q1: Float, val median: Float, val q3: Float, val max: Float)

/**
 * BoxChart is an Android View of interquartile data
 *
 * There are a few attributes that can be set on the Views XML
 *      medianColor
 *      medianWidth
 *      textColor
 *      whiskerColor
 *      boxColor
 */
class BoxChart(context: Context, attrs: AttributeSet? = null) : CandleStickChart(context, attrs) {
    @ColorInt
    private var medianColor: Int = Color.WHITE

    private var medianWidth: Float = 4f

    private val dataSet = CandleDataSet(mutableListOf(), "").apply {
        increasingPaintStyle = Paint.Style.FILL
        setDrawValues(false)
    }

    init {
        //Use BoxData attributes to set styling of CandleStickChart
        context.theme.obtainStyledAttributes(attrs, R.styleable.BoxChart, 0, 0).apply {
            medianColor = getColor(R.styleable.BoxChart_medianColor, medianColor)
            medianWidth = getFloat(R.styleable.BoxChart_medianWidth, medianWidth)
            axisLeft.textColor = getColor(R.styleable.BoxChart_textColor, Color.BLACK)
            dataSet.apply {
                shadowColor = getColor(R.styleable.BoxChart_whiskerColor, Color.BLACK)
                increasingColor = getColor(R.styleable.BoxChart_boxColor, Color.BLACK)
                valueTextColor = getColor(R.styleable.BoxChart_textColor, Color.BLACK)
            }

        }

        //Turn off unused CandleStickChart settings
        setTouchEnabled(false)
        isDragEnabled = false
        isDoubleTapToZoomEnabled = false
        setPinchZoom(false)
        setDrawBorders(false)
        legend.isEnabled = false
        axisLeft.setDrawAxisLine(false)
        axisRight.isEnabled = false
        xAxis.isEnabled = false

        //Set the CandleStickChart data to modifiable dataSet
        data = CandleData(dataSet)

        //Set initial BoxData
        set(BoxData(0f, 0f, 0f, 0f, 0f))
    }

    //Set the BoxData of the BoxChart
    fun set(boxData: BoxData) {
        //Update data to match boxData
        dataSet.entry = CandleEntry(0f, boxData.max, boxData.min, boxData.q1, boxData.q3)
        axisLeft.limit = LimitLine(boxData.median, "").apply {
            lineColor = medianColor
            lineWidth = medianWidth
        }

        //Redraw view
        data.notifyDataChanged()
        notifyDataSetChanged()
        invalidate()
        requestLayout()
    }

    private var CandleDataSet.entry: CandleEntry?
        set(value) {
            clear()
            value?.let { addEntry(it) }
        }
        get() = if (entryCount > 0) getEntryForIndex(0) else null

    private var AxisBase.limit: LimitLine?
        set(value) {
            removeAllLimitLines()
            value?.let { addLimitLine(it) }
        }
        get() = limitLines.firstOrNull()
}