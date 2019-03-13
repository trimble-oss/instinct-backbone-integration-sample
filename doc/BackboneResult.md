# Backbone Result Classes
The classes returned by [Backbone](VanillaBackbone.md) fetch methods.

## BackboneResult

BackboneResult is a map of [Backbone Key](BackboneDataTypes.md) to BackboneData.

Only data for the [Backbone Keys](BackboneDataTypes.md) passed into a fetch are present in the result.
If there is no element for an expected [Backbone Key](BackboneDataTypes.md) it means Backbone has not received any data for it.

## BackboneData
BackboneData contains:

* The time the data was sent from its source
* The time the data was received by the Backbone
* The actual value

Since different [Backbone Keys](BackboneDataTypes.md) can have different value types the valueAs method should be used.
```kotlin
val isEngineOn = engineOn.valueAs<Boolean>()
```
Internally BackboneData stores all values as JSON so `valueAs` is string interpolating it to the given type.
In other words valueAs will try to cast the value to the given type. 
```kotlin
val odometerInt = odometer.valueAs<Int>()
val odometerDouble = odometer.valueAs<Double>()
```
