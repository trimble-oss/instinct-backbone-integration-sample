# Entry

[androidJvm]\
data class Entry&lt;out T : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;(data: T?, receivedTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html), origin: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;)

Container for data retrieved from Backbone

## Parameters

androidJvm

| | |
|---|---|
| data | inserted into Backbone |
| receivedTime | when data was inserted into Backbone |
| origin | package that data was published from. Origin is a list because it is possible for packages to share a user id. In most cases the list will have only one element but if there are two then one of the packages published data. |

## Constructors

| | |
|---|---|
| Entry | [androidJvm]<br>fun &lt;out T : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; Entry(data: T?, receivedTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html), origin: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) |

## Properties

| Name | Summary |
|---|---|
| data | [androidJvm]<br>val data: T? |
| origin | [androidJvm]<br>val origin: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| receivedTime | [androidJvm]<br>val receivedTime: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) |