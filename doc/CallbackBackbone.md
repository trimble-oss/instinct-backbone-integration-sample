# CallbackBackbone

Provides all the methods provided by [Backbone](VanillaBackbone.md) and provides methods for asynchronous fetches.

## Periodic Fetch - Single Key

After each period completion [BackboneData](BackboneDataTypes.md) is fetched using a [Backbone Key](BackboneResult.md)
to indicate what data to retrieve. When the fetched [BackboneData](BackboneDataTypes.md) is not null it is passed into the 
callback function.

When you want the fetch to terminate call the stop method on the returned Query.

```kotlin
val query = callbackBackbone.periodicFetch(1000, ENGINE_HOURS_KEY) { hours ->
// do stuff with hours
}
...
query.stop()
```

## Periodic Fetch - Multiple Keys

After each period completion a [BackboneResult](BackboneDataTypes.md) is fetched using [Backbone Keys](BackboneResult.md)
to indicate what data to retrieve. The fetched [BackboneResult](BackboneDataTypes.md) is passed into the callback function.

When you want the fetch to terminate call the stop method on the returned Query.

```kotlin
val query = callbackBackbone.periodicFetch(1000, listOf(ENGINE_HOURS_KEY, ENGINE_SPEED_KMH_KEY)) { result ->
// do stuff with result
}
...
query.stop()
```

## Monitor Fetch - Single Key

Whenever there is a change in data indicated by [Backbone Key](BackboneResult.md) the [BackboneData](BackboneDataTypes.md) is 
fetched. The fetched [BackboneData](BackboneDataTypes.md) is passed into the callback function.

When you want the fetch to terminate call the stop method on the returned Query.

```kotlin
val query = callbackBackbone.monitorFetch(ENGINE_HOURS_KEY) { result ->
    // do stuff with result
}
...
query.stop()
```

## Monitor Fetch - Multiple Keys

Whenever there is a change in any data indicated by [Backbone Keys](BackboneResult.md) a [BackboneResult](BackboneDataTypes.md) is
 fetched. The fetched [BackboneResult](BackboneDataTypes.md) is passed into the callback function.

When you want the fetch to terminate call the stop method on the returned Query.

```kotlin
val query = callbackBackbone.monitorFetch(listOf(ENGINE_HOURS_KEY, ENGINE_ODOMETER_KM_KEY)) { result ->
    // do stuff with result
}
...
query.stop()
```

It is also possible to fetch different data than what is being monitored.

```kotlin
val query = callbackBackbone.monitorFetch(
        monitorKeys = listOf(IGNITION_KEY),
        fetchKeys = listOf(ENGINE_HOURS_KEY, ENGINE_ODOMETER_KM_KEY)
) { result ->
    // do stuff with result
}
...
query.stop()
```