## RxBackbone

Provides all the methods provided by [Backbone](VanillaBackbone.md) and provides methods for asynchronous fetches that return RxJava2 Observables.

## Periodic Fetch - Single Key

Returns an RxJava Observable that emits [BackboneData](BackboneResult.md) when the period elapses. No data is emitted 
when none can be found.

```kotlin
val observable = rxBackbone.periodicFetch(1, TimeUnit.SECONDS, ENGINE_HOURS_KEY)
```

<a name="rxBackbonePeriodicMultiFetch"/>

## Periodic Fetch - Multiple Keys

Returns an RxJava Observable that emits a [BackboneResult](BackboneResult.md) whenever the period elapses.

```kotlin
val observable = rxBackbone.periodicFetch(1, TimeUnit.SECONDS, listOf(ENGINE_HOURS_KEY, ENGINE_ODOMETER_KM_KEY))
```

<a name="rxBackboneMonitorSingleFetch"/>

## Monitor Fetch - Single Key

Returns an RxJava Observable that emits a [BackboneData](BackboneResult.md) whenever there is a change in monitored
data.

```kotlin
val observable = rxBackbone.monitorFetch(ENGINE_HOURS_KEY)
```

## Monitor Fetch - Multiple Keys

Returns an RxJava Observable that emits a [BackboneResult](BackboneResult.md) whenever there is a change in monitored
data.

```kotlin
val observable = rxBackbone.monitorFetch(listOf(ENGINE_HOURS_KEY, ENGINE_ODOMETER_KM_KEY))
```

It is also possible to fetch different data than what is being monitored.

```kotlin
val observable = rxBackbone.monitorFetch(
        monitorKeys = listOf(IGNITION_KEY),
        fetchKeys = listOf(ENGINE_HOURS_KEY, ENGINE_ODOMETER_KM_KEY)
) 
```