/*
 * © 2022 Trimble Inc. Used under license. All rights reserved. Unauthorized duplication, copying or use prohibited.
 */

package com.trimble.ttm.mepsampleapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.trimble.ttm.mepsampleapp.R

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

    private var tripTime: TextView
    private var tripDistance: TextView

    init {
        View.inflate(context, R.layout.trip_layout, this).apply {
            tripTime = findViewById(R.id.trip_time)
            tripDistance = findViewById(R.id.trip_distance)
        }
        set(Trip(0, 0))
    }

    fun set(trip: Trip) {
        tripTime.text = trip.duration
        tripDistance.text = trip.distance
        invalidate()
        requestLayout()
    }
}