# Backbone

Provides methods to synchronously fetch information from Backbone.

## Fetch - Single Key

Fetch [BackboneData](BackboneResult.md) using a single [Backbone Key](BackboneDataTypes.md) to indicate what data to retrieve.
If there is no data present in Backbone then null is returned.

```kotlin
val engineHours: BackboneData? = backbone.fetch(ENGINE_HOURS_KEY)
```

## Fetch - Multiple Keys

Fetch a [BackboneResult](BackboneResult.md) using [Backbone Keys](BackboneDataTypes.md) to indicate what data to retrieve.

```kotlin
val result:BackboneResult = backbone.fetch(listOf(ENGINE_HOURS_KEY, ENGINE_SPEED_KMH_KEY))
```