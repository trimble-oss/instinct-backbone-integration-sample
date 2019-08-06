# Trimble Mobile Ecosystem Data Backbone Sample App

![alt-text](../img/SampleAppView.png "Sample App Screenshot")

## Getting Started

You will not be able to build this project until you set up access to the Trimble Artifactory Repo.

Please follow the [Backbone Setup Guide](../BackboneGettingStarted.md).

## Overview

This sample app uses [Backbone](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-backbone/) to retrieve and monitor data stored on the Backbone App.

In order to effectively use [Backbone](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-backbone/) The most important things to learn are:
* What is a [Backbone.Retriever](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-backbone/-retriever/)
* Difference between [SingleEntryQuery](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-single-entry-query/) and [MultipleEntryQuery](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-multiple-entry-query/)
* When to monitor data for changes and when to fetch data periodically

## What is a Backbone.Retriever

Data in the Backbone app is stored as JSON and identified by a unique key. 

[Backbone.Retrievers](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-backbone/-retriever/) contain the unique key and a method to map the JSON into a pre-defined data object.

In other words when you ask backbone to retrieve [GpsDegrees](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-gps-degrees/-companion/) 
the returned [Backbone.Entry](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-backbone/-entry/) 
contains [GpsDegrees](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-gps-degrees/) as data not JSON.
```kotlin
val gps: Backbone.Entry<GpsDegrees>? = backbone.retrieveDataFor(GpsDegrees).fetch()
```

List of all [defined Backbone.Retrievers](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/)

**Note:** *All the [defined Backbone.Retrievers](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/) are [Companion Objects](https://kotlinlang.org/docs/tutorials/kotlin-for-py/objects-and-companion-objects.html#companion-objects) of the given data type. That's how [GpsDegrees](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-gps-degrees/) is a data class and a [Backbone.Retriever](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-backbone/-retriever/).*

## Difference between SingleKeyQuery and MultipleKeyQuery

The logic for updating speedometer, in the center of the screen:
```kotlin
backbone
    .retrieveDataFor(EngineSpeedKmh)
    .every(2, SECONDS)
    .handle { (speed, _) -> _speed.postValue(speed.value.toFloat()) }
```

The logic for updating trip (how far/long current trip is), at the top of the screen:
```kotlin
backbone
    .retrieveDataFor(EngineOdometerKm, TimeEngineOn)
    .every(1, MINUTES)
    .handle { result ->
        result[TimeEngineOn]?.let { (engineOn, _) ->
            _trip.postValue(
                updateTrip.with(
                    odometer = result[EngineOdometerKm]?.data?.value?.toInt() ?: 0,
                    timeEngineOn = engineOn.value
                )
            )
        }
    }
```

To calculate the duration of the current trip both [EngineOdometerKm](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-engine-odometer-km/) and [TimeEngineOn](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-time-engine-on) are needed.
Unlike speed which uses a [SingleEntryQuery](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-single-entry-query/), trip uses a [MultipleEntryQuery](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-multiple-entry-query/).

With a [SingleEntryQuery](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-single-entry-query/) a [Backbone.Entry](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-backbone/-entry/) is returned directly 
but for a [MultipleEntryQuery](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-multiple-entry-query/) a [MultipleEntryQuery.Result](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-multiple-entry-query/-result) is returned.
A [MultipleEntryQuery.Result](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-multiple-entry-query/-result) contains a [Backbone.Entry](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-backbone/-entry/) for each queried [Backbone.Retriever](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-backbone/-retriever/).

Using a [MultipleEntryQuery](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-multiple-entry-query/) makes it easier to keep different data sources in sync. 
It also makes code easier to understand. 
If we wanted to use only [SingleEntryQueries](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-single-entry-query/) to calculate trip, we would have to do something like the much worse:
```kotlin
private var shouldUpdate = false
private var odometer: Int = 0
private var timeEngineOn: Long = 0

backbone.retrieveDataFor(EngineOdometerKm)
    .every(1, MINUTES)
    .handle {
        odometer = it.data.value.toInt()
        if (shouldUpdate) {
            _trip.postValue(updateTrip.with(odometer, timeEngineOn))
            shouldUpdate = false
        } else {
            shouldUpdate = true
        }
    }

backbone.retrieveDataFor(TimeEngineOn)
    .every(1, MINUTES)
    .handle {
        timeEngineOn = it.data.value
        if (shouldUpdate) {
            _trip.postValue(updateTrip.with(odometer, timeEngineOn))
            shouldUpdate = false
        } else {
            shouldUpdate = true
        }
    }
```

**Note:** *If Backbone does not have data for a desired [Backbone.Retriever](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-backbone/-retriever/) then the value in the [MultipleEntryQuery.Result](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-multiple-entry-query/-result) will be null.*
```kotlin
val odometer = result[EngineOdometerKm]?.data?.value?.toInt() ?: 0
```

**Note:** *[Backbone.Entry](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-backbone/-entry/) is a [Data Class](https://kotlinlang.org/docs/reference/data-classes.html#data-classes) so [destructuring declaration](https://kotlinlang.org/docs/reference/data-classes.html#data-classes-and-destructuring-declarations) can be used.*
```kotlin
backbone
    .retrieveDataFor(EngineSpeedKmh)
    .every(2, SECONDS)
    .handle { (speed, _) ->  }
```
## When to monitor data for changes and when to fetch data periodically

The logic for updating trip, at the top of the screen, retrieves [EngineOdometerKm](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-engine-odometer-km/) and [TimeEngineOn](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-time-engine-on) every minute.
```kotlin
backbone
    .retrieveDataFor(EngineOdometerKm, TimeEngineOn)
    .every(1, MINUTES)
    .handle { result ->
        result[TimeEngineOn]?.let { (engineOn, _) ->
            _trip.postValue(
                updateTrip.with(
                    odometer = result[EngineOdometerKm]?.data?.value?.toInt() ?: 0,
                    timeEngineOn = engineOn.value
                )
            )
        }
    }
```

The logic for updating Ignition, on the right of the screen, retrieves [Ignition](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-ignition/) and [EngineOn](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-engine-on/) whenever one of the values change.
```kotlin
backbone
    .monitorChangesInDataFor(Ignition)
    .alsoMonitor(EngineOn)
    .handle { result ->
        _ignition.postValue(
            when {
                result[EngineOn]?.data?.value == true -> IgnitionState.ENGINE_ON
                result[Ignition]?.data?.value == true -> IgnitionState.ACCESSORY
                else -> IgnitionState.OFF
            }
        )
    }
```

In most vehicles the [EngineOdometerKm](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-engine-odometer-km/) and [TimeEngineOn](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-time-engine-on) are updated every 2 seconds.

If trip was updated every time [EngineOdometerKm](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-engine-odometer-km/) or [TimeEngineOn](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-time-engine-on) was changed, 
there would be many updates, with no UI change, because trip shows travel time in seconds and distance in KM.

The sample app retrieves trip data every minute to limit the amount of expensive Inter-Process Communication (IPC).

On the other hand, [Ignition](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-ignition/) and [EngineOn](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-engine-on/) change only at the ends of a driver's trip.
If the query ran periodically there would be many updates with no UI change. 
It would also be likely for [Ignition](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-ignition/) or [EngineOn](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-engine-on/) to change between period completions, causing the UI update to be delayed.

Instead of periodically forcing a fetch, the sample app asks Backbone to send [Ignition](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-ignition/) and [EngineOn](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api.data/-engine-on/) when one of them changes.

**Note:** *It's possible to create a [MultipleEntryQuery](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-multiple-entry-query/) that retrieves extra [Backbone.Entries](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-backbone/-entry/) when monitored data changes.*

```kotlin
backbone
    .monitorChangesInDataFor(Ignition)
    .alsoRetrieve(EngineOdometerKm, EngineSpeedKmh)
    .handle { result ->
            val ignition = result[Ignition]
            val odometer = result[EngineOdometerKm]
    }
```

**Note:** *It's possible to create a Query that both monitors data and runs periodically. When a change occurs then the period is reset. The code below fetches Ignition at least once a minute.*

```kotlin
backbone
    .monitorChangesInDataFor(Ignition)
    .every(1, MINUTES)
    .handle { (ignition, _) ->
    
    }
```

