# Unit Testing with the Backbone

This guide will show some basic unit testing you can include in your app that interacts with the Backbone.
All examples are using [JUnit4](https://junit.org/junit4/) and [mockk](https://mockk.io).
You can see also view actual tests written in the sample app.

## Mocking the Backbone

The first thing you'll want to do is mock the BackboneFactory. Note that this is a static class.
 You only need to do this once per test, so using [@Before](https://junit.org/junit4/javadoc/4.12/org/junit/Before.html) or similar
can help reduce duplication. [Mockk has some special
annotations](https://mockk.io/#annotations) to help even further.
Depending on which flavor of Backbone you are using, you can now also mock the Backbone:

```kotlin
val callbackBackbone = mockk<CallbackBackbone>(relaxed = true)
every { BackboneFactory.callbackBackbone(any()) } returns callbackBackbone

val vanillaBackbone = mockk<Backbone>(relaxed = true)
every { BackboneFactory.backbone(any()) } returns vanillaBackbone

val rxBackbone = mockk<RxBackbone>(relaxed = true)
every { BackboneFactory.rxBackbone(any()) } returns rxBackbone
```

## Mocking Synchronous Fetch Results

All flavors can grab the latest data from the Backbone using fetch.

You probably won't be interacting with a raw BackboneData object much, but rather the value inside of it via valueAs. This makes mocking fetched data very easy.

Maybe your app has some logic to show some warning based on the odometer going over some threshold:

```kotlin
@RelaxedMockK
private lateinit var backbone: Backbone

@Before
fun before() {
    MockKAnnotations.init(this)
    mockkStatic(BackboneFactory::class)
    every { BackboneFactory.backbone(any()) } returns backbone
}

@Test
fun `should enable warning when odometer above threshold`() {
    //mock fetch result with a value to test against
    every { backbone.fetch(ENGINE_ODOMETER_KM_KEY)?.valueAs<Double>() } returns ODOMETER_THRESHOLD + 1

    //init a real view model with a mocked Application
    val viewModel = VanillaViewModel(mockk(relaxed = true))

    assertTrue(viewModel.warningEnabled)
}
```


## Mocking CallbackBackbone Results

If you are using the CallbackBackbone to periodically fetch or monitor results, your tests look a bit different due to the asynchronous callback.

Your app will be supplying the callback lambda to observe new data, so in the test, we need to [capture](https://mockk.io/#capturing) the lambda via `captureLambda` and invoke it with mocked data:

```kotlin
//must include this to make our tests synchronous!
@Rule
@JvmField
val rule = InstantTaskExecutorRule()

@RelaxedMockK
private lateinit var backbone: CallbackBackbone

@Before
fun before() {
    MockKAnnotations.init(this)
    mockkStatic(BackboneFactory::class)
    every { BackboneFactory.callbackBackbone(any()) } returns backbone
}

@Test
fun `should enable warning when odometer above threshold`() {
    //this will store the real (not mocked) callback that your app is using
    lateinit var callback: (BackboneData) -> Unit

    //stub periodFetch call and capture the callback lambda
    every { backbone.periodicFetch(any(), ENGINE_ODOMETER_KM_KEY, captureLambda()) } answers {
        //store the real callback so we can invoke it with mocked data
        callback = lambda<(BackboneData) -> Unit>().captured

        //return a mocked Query to satisfy the API
        //you could also verify { query.stop() }
        mockk(relaxed = true)
    }

    //create a mock BackboneData with the odometer reading to test against
    val odometerReading = mockk<BackboneData>(relaxed = true).apply {
        every { valueAs<Double>() } returns ODOMETER_THRESHOLD + 1
    }

    //init a real view model with a mocked Application
    val viewModel = CallbackViewModel(mockk(relaxed = true))

    //invoke your callback
    callback(odometerReading)

    assertTrue(viewModel.warningEnabled)
}
```

## Mocking RxBackbone Results

Finally, if you are using the RxBackbone, your tests will also look different. You will need to create a [PublishSubject](http://reactivex.io/RxJava/javadoc/io/reactivex/subjects/PublishSubject.html) and call `onNext` with your mocked data:

```kotlin
@RelaxedMockK
private lateinit var rxBackbone: RxBackbone

private val backboneDataSubject = PublishSubject.create<BackboneData>()

@Before
fun before() {
    MockKAnnotations.init(this)
    mockkStatic(BackboneFactory::class)
    every { BackboneFactory.rxBackbone(any()) } returns rxBackbone
}

@Test
fun `should enable warning when odometer above threshold`() {
    every { rxBackbone.monitorFetch(ENGINE_ODOMETER_KM_KEY) } returns backboneDataSubject

    //init a real view model with a mocked Application
    val viewModel = RxViewModel(mockk(relaxed = true))

    //create a mock BackboneData with the odometer reading to test against
    val odometerReading = mockk<BackboneData>(relaxed = true).apply {
        every { valueAs<Double>() } returns ODOMETER_THRESHOLD + 1
    }

    //emit the mocked data
    backboneDataSubject.onNext(odometerReading)

    assertTrue(viewModel.warningEnabled)
}
```