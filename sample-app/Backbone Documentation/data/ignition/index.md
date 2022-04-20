# Ignition

[androidJvm]\
data class Ignition(value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), sentTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)) : PublisherSensorInput&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;

PublisherSensorInput representing the status of a vehicle's ignition

## Constructors

| | |
|---|---|
| Ignition | [androidJvm]<br>fun Ignition(value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), sentTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)) |

## Types

| Name | Summary |
|---|---|
| Companion | [androidJvm]<br>object Companion : Backbone.Retriever&lt;Ignition&gt; <br>Backbone.Retriever for Ignition |

## Functions

| Name | Summary |
|---|---|
| asJSON | [androidJvm]<br>open override fun asJSON(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Formats data as JSON to store in Backbone |

## Properties

| Name | Summary |
|---|---|
| key | [androidJvm]<br>open override val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>identifies the Backbone data |
| sentTime | [androidJvm]<br>open override val sentTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)<br>when status was set |
| value | [androidJvm]<br>open override val value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>true when ignition on false when not |