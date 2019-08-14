/*
 * © 2019 Trimble Inc. Used under license. All rights reserved. Unauthorized duplication, copying or use prohibited.
 */

package com.trimble.ttm.mepsampleapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.trimble.ttm.backbone.api.*
import com.trimble.ttm.backbone.api.data.*
import com.trimble.ttm.mepsampleapp.view.BoxData
import com.trimble.ttm.mepsampleapp.view.IgnitionState
import com.trimble.ttm.mepsampleapp.view.Trip
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class MainViewModelIgnitionTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var observer: Observer<IgnitionState>

    @RelaxedMockK
    private lateinit var result: MultipleEntryQuery.Result

    private lateinit var callback: (MultipleEntryQuery.Result) -> Unit

    @Before
    fun before() {
        MockKAnnotations.init(this)

        mockkStatic("com.trimble.ttm.backbone.api.BackboneFactory")
        val backbone = mockk<Backbone>(relaxed = true) {
            every { monitorChangesInDataFor(Ignition) } returns mockk {
                every { alsoMonitor(EngineOn) } returns mockk {
                    every { handle(captureLambda()) } answers {
                        callback = lambda<(MultipleEntryQuery.Result) -> Unit>().captured
                        mockk(relaxed = true)
                    }

                }
            }
        }
        every { BackboneFactory.backbone(any()) } returns backbone

        MainViewModel(mockk(relaxed = true)).ignition.observeForever(observer)
    }

    @After
    fun after() {
        unmockkAll()
    }

    @Test
    fun `ignition should emit engine on when backbone says it is`() {
        every { result[EngineOn] } returns Backbone.Entry(EngineOn(true, Date()), Date())

        callback(result)

        verify { observer.onChanged(IgnitionState.ENGINE_ON) }
    }

    @Test
    fun `ignition should emit accessory when backbone says engine off and ignition on`() {
        every { result[EngineOn] } returns Backbone.Entry(EngineOn(false, Date()), Date())
        every { result[Ignition] } returns Backbone.Entry(Ignition(true, Date()), Date())

        callback(result)

        verify { observer.onChanged(IgnitionState.ACCESSORY) }
    }

    @Test
    fun `ignition should emit off when backbone says engine off and ignition off`() {
        every { result[EngineOn] } returns Backbone.Entry(EngineOn(false, Date()), Date())
        every { result[Ignition] } returns Backbone.Entry(Ignition(false, Date()), Date())

        callback(result)

        verify { observer.onChanged(IgnitionState.OFF) }
    }

    @Test
    fun `ignition should emit off when backbone is missing engine and ignition status`() {
        every { result[EngineOn] } returns null
        every { result[Ignition] } returns null

        callback(result)

        verify { observer.onChanged(IgnitionState.OFF) }
    }
}

class MainViewModelSpeedTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var observer: Observer<Float>

    @RelaxedMockK
    private lateinit var callback: (Backbone.Entry<EngineSpeedKmh>) -> Unit

    @Before
    fun before() {
        MockKAnnotations.init(this)

        mockkStatic("com.trimble.ttm.backbone.api.BackboneFactory")
        val backbone = mockk<Backbone>(relaxed = true) {
            every { retrieveDataFor(EngineSpeedKmh) } returns mockk {
                every { every(any(), any()) } returns this
                every { handle(captureLambda()) } answers {
                    callback = lambda<(Backbone.Entry<EngineSpeedKmh>) -> Unit>().captured
                    mockk(relaxed = true)
                }
            }
        }
        every { BackboneFactory.backbone(any()) } returns backbone

        MainViewModel(mockk(relaxed = true)).speed.observeForever(observer)
    }

    @After
    fun after() {
        unmockkAll()
    }

    @Test
    fun `speed should emit backbone speed reading`() {
        callback(Backbone.Entry(EngineSpeedKmh(110.2, Date()), Date()))

        verify { observer.onChanged(110.2f) }
    }
}

class MainViewModelTripTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var observer: Observer<Trip>

    @RelaxedMockK
    private lateinit var result: MultipleEntryQuery.Result

    private val callback = slot<(MultipleEntryQuery.Result) -> Unit>()

    @Before
    fun before() {
        MockKAnnotations.init(this)

        mockkStatic("com.trimble.ttm.backbone.api.BackboneFactory")
        val backbone = mockk<Backbone>(relaxed = true) {
            every { retrieveDataFor(EngineOdometerKm) } returns mockk {
                every { alsoRetrieve(TimeEngineOn) } returns mockk {
                    every { every(any(), any()) } returns this
                    every { handle(capture(callback)) } returns mockk()
                }
            }
        }
        every { BackboneFactory.backbone(any()) } returns backbone

        MainViewModel(mockk(relaxed = true)).trip.observeForever(observer)
    }

    @After
    fun after() {
        unmockkAll()
    }

    @Test
    fun `trip should emit Trip based on odometer and time engine on`() {
        every { result[EngineOdometerKm] } returns Backbone.Entry(EngineOdometerKm(4242.42, Date()), Date())

        every { result[TimeEngineOn] } returns Backbone.Entry(TimeEngineOn(2, Date()), Date())

        callback.captured(result)

        verify { observer.onChanged(Trip(2, 0)) }
    }
}

class MainViewModelLatencyTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var observer: Observer<BoxData>

    private val callback = slot<(Backbone.Entry<GpsDegrees>) -> Unit>()

    @Before
    fun before() {
        MockKAnnotations.init(this)

        mockkStatic("com.trimble.ttm.backbone.api.BackboneFactory")
        val backbone = mockk<Backbone>(relaxed = true) {
            every { monitorChangesInDataFor(GpsDegrees) } returns mockk {
                every { handle(capture(callback)) } returns mockk()
            }
        }
        every { BackboneFactory.backbone(any()) } returns backbone
    }

    @After
    fun after() {
        unmockkAll()
    }

    @Test
    fun `should emit BoxData with receivedTime minus sentTime`() {
        MainViewModel(mockk(relaxed = true)).latency.observeForever(observer)

        callback.captured(Backbone.Entry(GpsDegrees(0.0, 0.0, 0.0, Date(), Date(1000000)), Date(1001000)))

        verify(exactly = 1) { observer.onChanged(BoxData(1f, 1f, 1f, 1f, 1f)) }
    }
}