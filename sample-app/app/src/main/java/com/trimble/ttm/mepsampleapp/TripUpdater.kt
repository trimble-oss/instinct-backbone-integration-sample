/*
 * © 2019 Trimble Inc. Used under license. All rights reserved. Unauthorized duplication, copying or use prohibited.
 */

package com.trimble.ttm.mepsampleapp

import com.trimble.ttm.mepsampleapp.view.Trip

class TripUpdater {
    private var distanceAtStart: Int = 0
    private var prevTimeOn: Long = Long.MAX_VALUE

    fun with(odometer: Int, timeEngineOn: Long): Trip {
        if (timeEngineOn < prevTimeOn) distanceAtStart = odometer

        prevTimeOn = timeEngineOn

        return Trip(timeEngineOn, odometer - distanceAtStart)
    }
}