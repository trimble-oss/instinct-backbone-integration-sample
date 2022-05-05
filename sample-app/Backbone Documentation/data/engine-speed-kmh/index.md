# EngineSpeedKmh

[androidJvm]\
data class EngineSpeedKmh(value: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), sentTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)) : PublisherSensorInput&lt;[Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)&gt;

PublisherSensorInput representing the KM/H the vehicle is traveling

## Constructors

| | |
|---|---|
| EngineSpeedKmh | [androidJvm]<br>fun EngineSpeedKmh(value: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), sentTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)) |

## Types

| Name | Summary |
|---|---|
| Companion | [androidJvm]<br>object Companion : Backbone.Retriever&lt;[EngineSpeedKmh](index.md)&gt; <br>Backbone.Retriever for EngineSpeedKmh |

## Functions

| Name | Summary |
|---|---|
| asJSON | [androidJvm]<br>open override fun asJSON(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Formats data as JSON to store in Backbone |

## Properties

| Name | Summary |
|---|---|
| key | [androidJvm]<br>open override val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>identifies the Backbone data |
| sentTime | [androidJvm]<br>open override val sentTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)<br>when most recent speed was set |
| value | [androidJvm]<br>open override val value: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)<br>representing the KM/H the vehicle is traveling |