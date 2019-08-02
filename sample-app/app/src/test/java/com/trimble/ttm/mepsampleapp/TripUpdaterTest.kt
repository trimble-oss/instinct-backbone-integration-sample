/*
 * © 2019 Trimble Inc. Used under license. All rights reserved. Unauthorized duplication, copying or use prohibited.
 */

package com.trimble.ttm.mepsampleapp

import com.trimble.ttm.mepsampleapp.view.Trip
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test
import java.util.*

class TripUpdaterTest {
    private lateinit var tripUpdater: TripUpdater

    @Before
    fun before() {
        tripUpdater = TripUpdater()
    }

    @Test
    fun `should return Trip after first result`() {
        val trip = tripUpdater.with(
            odometer = 4242,
            timeEngineOn = 2
        )

        assertEquals(Trip(2, 0), trip)
    }

    @Test
    fun `should return correct Trip after multiple results`() {
        tripUpdater.with(
            odometer = 4242,
            timeEngineOn = 2
        )

        val trip = tripUpdater.with(
            odometer = 4243,
            timeEngineOn = 8
        )

        assertEquals(Trip(8, 1), trip)
    }
}