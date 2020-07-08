/*
 * © 2019 Trimble Inc. Used under license. All rights reserved. Unauthorized duplication, copying or use prohibited.
 */

package com.trimble.ttm.mepsampleapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.trimble.ttm.mepsampleapp.R
import kotlinx.android.synthetic.main.trip_layout.view.*

data class Trip(val durationSeconds: Long, val distanceKm: Int) {
    val duration: String = StringBuilder()
        .append(durationSeconds / 3600)
        .append(":")
        .append((durationSeconds % 3600 / 60).padZero(2))
        .toString()

    val distance: String = "${distanceKm.padZero(4)} km"

    private fun Number.padZero(length: Int): String = toString().padStart(length, '0')
}

class TripView(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {
    init {
        View.inflate(context, R.layout.trip_layout, this)
        set(Trip(0, 0))
    }

    fun set(trip: Trip) {
        trip_time.text = trip.duration
        trip_distance.text = trip.distance
        invalidate()
        requestLayout()
    }
}