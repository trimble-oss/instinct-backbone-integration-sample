/*
 * © 2019 Trimble Inc. Used under license. All rights reserved. Unauthorized duplication, copying or use prohibited.
 */

package com.trimble.ttm.mepsampleapp

import com.trimble.ttm.mepsampleapp.view.BoxData
import junit.framework.TestCase.assertEquals
import org.junit.Test


class LatencyCalculatorTest {
    @Test
    fun `should return zeroed BoxData when add has never been called`() {
        assertEquals(BoxData(0f, 0f, 0f, 0f, 0f), LatencyCalculator(1).data)
    }

    @Test
    fun `should return BoxData when one time added`() {
        val latency = LatencyCalculator(8).apply {
            add(0.111f)
        }

        assertEquals(BoxData(0.111f, 0.111f, 0.111f, 0.111f, 0.111f), latency.data)
    }

    @Test
    fun `should calculate BoxData for added times`() {
        val latency = LatencyCalculator(8).apply {
            add(0.444f)
            add(0.778f)
            add(0.111f)
            add(0.889f)
            add(0.222f)
            add(0.333f)
            add(0.677f)
        }

        assertEquals(BoxData(0.111f, 0.222f, 0.444f, .778f, .889f), latency.data)
    }

    @Test
    fun `should calculate BoxData for times in window`() {
        val latency = LatencyCalculator(7).apply {
            add(0.1f)
            add(0.444f)
            add(0.778f)
            add(0.111f)
            add(0.889f)
            add(0.222f)
            add(0.333f)
            add(0.677f)
        }

        assertEquals(BoxData(0.111f, 0.222f, 0.444f, .778f, .889f), latency.data)
    }
}