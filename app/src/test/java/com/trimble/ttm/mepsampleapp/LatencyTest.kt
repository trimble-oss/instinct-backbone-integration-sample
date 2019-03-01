package com.trimble.ttm.mepsampleapp

import com.trimble.ttm.mepsampleapp.view.BoxData
import junit.framework.TestCase.assertEquals
import org.junit.Test


class LatencyTest {
    @Test
    fun `should return zeroed BoxData when messageReceived has never been called`() {
        assertEquals(BoxData(0f, 0f, 0f, 0f, 0f), Latency(1).data)
    }

    @Test
    fun `should return zeroed BoxData when messageReceived has only been called once`() {
        val latency = Latency(8).apply {
            add(1000L)
        }
        assertEquals(BoxData(0f, 0f, 0f, 0f, 0f), latency.data)
    }

    @Test
    fun `should calculate BoxData for added times`() {
        val latency = Latency(8).apply {
            add(1000L)
            add(1111L)
            add(2000L)
            add(2222L)
            add(3000L)
            add(3333L)
            add(4000L)
            add(4444L)
        }

        assertEquals(BoxData(0.111f, 0.222f, 0.444f, .778f, .889f), latency.data)
    }

    @Test
    fun `should calculate BoxData for times in window`() {
        val latency = Latency(8).apply {
            add(100L)
            add(200L)
            add(300L)
            add(1000L)
            add(1111L)
            add(2000L)
            add(2222L)
            add(3000L)
            add(3333L)
            add(4000L)
            add(4444L)
        }

        assertEquals(BoxData(0.111f, 0.222f, 0.444f, .778f, .889f), latency.data)
    }
}