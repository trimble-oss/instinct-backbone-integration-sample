package com.trimble.ttm.mepsampleapp

import android.os.SystemClock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.trimble.ttm.backbone.api.*
import com.trimble.ttm.backbone.api.BackboneKeys.ENGINE_ODOMETER_KM_KEY
import com.trimble.ttm.backbone.api.BackboneKeys.ENGINE_ON_KEY
import com.trimble.ttm.backbone.api.BackboneKeys.ENGINE_SPEED_KMH_KEY
import com.trimble.ttm.backbone.api.BackboneKeys.GPS_DEGREES_KEY
import com.trimble.ttm.backbone.api.BackboneKeys.IGNITION_KEY
import com.trimble.ttm.backbone.api.BackboneKeys.TIME_ENGINE_ON_SECONDS_KEY
import com.trimble.ttm.mepsampleapp.view.BoxData
import com.trimble.ttm.mepsampleapp.view.IgnitionState
import com.trimble.ttm.mepsampleapp.view.Trip
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*
import java.util.concurrent.TimeUnit.MINUTES
import java.util.concurrent.TimeUnit.SECONDS

class MainViewModelIgnitionTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val backboneResultSubject = PublishSubject.create<BackboneResult>()

    @RelaxedMockK
    private lateinit var observer: Observer<IgnitionState>

    @Before
    fun before() {
        MockKAnnotations.init(this)

        mockkStatic("com.trimble.ttm.backbone.api.BackboneFactory")
        val backbone = mockk<RxBackbone>(relaxed = true) {
            every { monitorFetch(listOf(IGNITION_KEY, ENGINE_ON_KEY)) } returns backboneResultSubject
        }
        every { BackboneFactory.rxBackbone(any()) } returns backbone

        MainViewModel(mockk(relaxed = true)).ignition.observeForever(observer)
    }

    @After
    fun after() {
        unmockkAll()
    }

    @Test
    fun `ignition should emit engine on when backbone says it is`() {
        backboneResultSubject.onNext(mapOf(ENGINE_ON_KEY to status(true)))

        verify { observer.onChanged(IgnitionState.ENGINE_ON) }
    }

    @Test
    fun `ignition should emit accessory when backbone says engine off and ignition on`() {
        backboneResultSubject.onNext(
            mapOf(
                ENGINE_ON_KEY to status(false),
                IGNITION_KEY to status(true)
            )
        )

        verify { observer.onChanged(IgnitionState.ACCESSORY) }
    }

    @Test
    fun `ignition should emit off when backbone says engine off and ignition off`() {
        backboneResultSubject.onNext(
            mapOf(
                ENGINE_ON_KEY to status(false),
                IGNITION_KEY to status(false)
            )
        )

        verify { observer.onChanged(IgnitionState.OFF) }
    }

    @Test
    fun `ignition should emit off when backbone is missing engine and ignition status`() {
        backboneResultSubject.onNext(mapOf())

        verify { observer.onChanged(IgnitionState.OFF) }
    }

    private fun status(reading: Boolean) =
        BackboneData("{$DATA_KEY:$reading, $MESSAGE_SENT_TIME_KEY: 1234567890}", Date())
}

class MainViewModelSpeedTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var observer: Observer<Float>

    private val backboneDataSubject = PublishSubject.create<BackboneData>()

    @Before
    fun before() {
        MockKAnnotations.init(this)

        mockkStatic("com.trimble.ttm.backbone.api.BackboneFactory")
        val backbone = mockk<RxBackbone>(relaxed = true) {
            every { periodicFetch(2, SECONDS, ENGINE_SPEED_KMH_KEY) } returns backboneDataSubject
        }
        every { BackboneFactory.rxBackbone(any()) } returns backbone

        MainViewModel(mockk(relaxed = true)).speed.observeForever(observer)
    }

    @After
    fun after() {
        unmockkAll()
    }

    @Test
    fun `speed should emit backbone speed reading`() {
        backboneDataSubject.onNext(speed(110.2))

        verify { observer.onChanged(110.2f) }
    }

    private fun speed(reading: Double) =
        BackboneData("{$DATA_KEY:$reading, $MESSAGE_SENT_TIME_KEY: 1234567890}", Date())
}

class MainViewModelTripTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var observer: Observer<Trip>

    private val backboneResultSubject = PublishSubject.create<BackboneResult>()

    @Before
    fun before() {
        MockKAnnotations.init(this)

        mockkStatic("com.trimble.ttm.backbone.api.BackboneFactory")
        val backbone = mockk<RxBackbone>(relaxed = true) {
            every {
                periodicFetch(
                    1,
                    MINUTES,
                    listOf(ENGINE_ODOMETER_KM_KEY, TIME_ENGINE_ON_SECONDS_KEY)
                )
            } returns backboneResultSubject
        }
        every { BackboneFactory.rxBackbone(any()) } returns backbone

        MainViewModel(mockk(relaxed = true)).trip.observeForever(observer)
    }

    @After
    fun after() {
        unmockkAll()
    }

    @Test
    fun `trip should emit Trip based on odometer and time engine on`() {
        backboneResultSubject.onNext(
            mapOf(
                ENGINE_ODOMETER_KM_KEY to odometer(4242.42),
                TIME_ENGINE_ON_SECONDS_KEY to timeEngineOn(2)
            )
        )

        verify { observer.onChanged(Trip(2, 0)) }
    }

    private fun odometer(reading: Double) =
        BackboneData("{$DATA_KEY:$reading, $MESSAGE_SENT_TIME_KEY: 1234567890}", Date())

    private fun timeEngineOn(reading: Long) =
        BackboneData("{$DATA_KEY:$reading, $MESSAGE_SENT_TIME_KEY: 1234567890}", Date())
}

class MainViewModelLatencyTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var observer: Observer<BoxData>

    private val backboneDataSubject = PublishSubject.create<BackboneData>()

    @Before
    fun before() {
        MockKAnnotations.init(this)

        mockkStatic(
            "com.trimble.ttm.backbone.api.BackboneFactory",
            "android.os.SystemClock"
        )
        val backbone = mockk<RxBackbone>(relaxed = true) {
            every { monitorFetch(GPS_DEGREES_KEY) } returns backboneDataSubject
        }
        every { BackboneFactory.rxBackbone(any()) } returns backbone
        every { SystemClock.uptimeMillis() } returns 0
    }

    @After
    fun after() {
        unmockkAll()
    }

    @Test
    fun `should emit BoxData when latency observed`() {
        MainViewModel(mockk(relaxed = true)).latency.observeForever(observer)

        verify(exactly = 1) { observer.onChanged(any()) }
    }

    @Test
    fun `should emit BoxData when data emitted`() {
        MainViewModel(mockk(relaxed = true)).latency.observeForever(observer)

        backboneDataSubject.onNext(BackboneData("", Date()))

        verify(exactly = 2) { observer.onChanged(any()) }
    }

}