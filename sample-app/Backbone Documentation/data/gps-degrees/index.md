# GpsDegrees

[androidJvm]\
data class GpsDegrees(latitude: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), longitude: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), fixAccuracyMeters: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), fixTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html), sentTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)) : PublisherSensorInput&lt;[JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html)&gt;

PublisherSensorInput representing the global position of a vehicle

## Constructors

| | |
|---|---|
| GpsDegrees | [androidJvm]<br>fun GpsDegrees(latitude: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), longitude: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), fixAccuracyMeters: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), fixTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html), sentTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)) |

## Types

| Name | Summary |
|---|---|
| Companion | [androidJvm]<br>object Companion : Backbone.Retriever&lt;GpsDegrees&gt; <br>Backbone.Retriever for GpsDegrees |

## Functions

| Name | Summary |
|---|---|
| asJSON | [androidJvm]<br>open override fun asJSON(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Formats data as JSON to store in Backbone |

## Properties

| Name | Summary |
|---|---|
| fixAccuracyMeters | [androidJvm]<br>val fixAccuracyMeters: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)<br>how accurate the position is in meters |
| fixTime | [androidJvm]<br>val fixTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)<br>when the position was established |
| key | [androidJvm]<br>open override val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>identifies the Backbone data |
| latitude | [androidJvm]<br>val latitude: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)<br>in decimal degrees |
| longitude | [androidJvm]<br>val longitude: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)<br>in decimal degrees |
| sentTime | [androidJvm]<br>open override val sentTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)<br>when most position was set |