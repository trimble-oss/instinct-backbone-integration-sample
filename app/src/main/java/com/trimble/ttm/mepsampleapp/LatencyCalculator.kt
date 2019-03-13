package com.trimble.ttm.mepsampleapp

import com.trimble.ttm.mepsampleapp.view.BoxData


class LatencyCalculator(private val maxWindowSize: Int) {
    private val window = mutableListOf<Float>()

    val data: BoxData
        get() =
            if (window.isEmpty()) BoxData(0f, 0f, 0f, 0f, 0f)
            else window
                .sorted()
                .run {
                    BoxData(
                        min = first(),
                        q1 = quartile(1),
                        median = quartile(2),
                        q3 = quartile(3),
                        max = last()
                    )
                }

    fun add(seconds: Float) {
        window.add(seconds)
        if (window.size > maxWindowSize) window.removeAt(0)
    }

    private fun <T> List<T>.quartile(q: Int): T = get(size * q / 4)
}