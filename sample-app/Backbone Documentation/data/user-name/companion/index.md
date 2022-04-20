# Companion

[androidJvm]\
object Companion : Backbone.Retriever&lt;[Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), UserName&gt;&gt;

Backbone.Retriever to get the human name for all users. A Map of Instinct user ID to UserName is retrieved.

## Functions

| Name | Summary |
|---|---|
| convert | [androidJvm]<br>open override fun convert(json: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), UserName&gt; |
| monitorFields | [androidJvm]<br>fun monitorFields(vararg fields: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Backbone.Retriever&lt;[Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), UserName&gt;&gt;<br>Monitor specific fields of the Backbone data for changes. By setting monitorFields the query becomes more specific and will only be notified of changes when the specific fields are modified. If monitorFields are not set then any change in the Backbone data will trigger a change. |

## Properties

| Name | Summary |
|---|---|
| fields | [androidJvm]<br>open override val fields: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>identifies specific fields in the Backbone data |
| key | [androidJvm]<br>open override val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>identifies the data in Backbone |