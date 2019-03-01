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

class BoxChart(context: Context, attrs: AttributeSet? = null) : CandleStickChart(context, attrs) {
    @ColorInt
    private var medianColor: Int = Color.WHITE

    private var medianWidth: Float = 4f

    private val dataSet = CandleDataSet(mutableListOf(), "").apply {
        increasingPaintStyle = Paint.Style.FILL
        setDrawValues(false)
    }

    init {
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

        setTouchEnabled(false)
        isDragEnabled = false
        isDoubleTapToZoomEnabled = false
        setPinchZoom(false)
        setDrawBorders(false)
        legend.isEnabled = false
        axisLeft.setDrawAxisLine(false)
        axisRight.isEnabled = false
        xAxis.isEnabled = false

        data = CandleData(dataSet)
        set(BoxData(0f, 0f, 0f, 0f, 0f))
    }

    fun set(boxData: BoxData) {
        dataSet.entry = CandleEntry(boxData.median, boxData.max, boxData.min, boxData.q1, boxData.q3)
        axisLeft.limit = LimitLine(boxData.median, "").apply {
            lineColor = medianColor
            lineWidth = medianWidth
        }
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