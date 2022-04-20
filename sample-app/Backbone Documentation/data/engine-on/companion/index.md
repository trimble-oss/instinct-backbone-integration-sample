# Companion

[androidJvm]\
object Companion : Backbone.Retriever&lt;EngineOn&gt;

Backbone.Retriever for EngineOn

## Functions

| Name | Summary |
|---|---|
| convert | [androidJvm]<br>open override fun convert(json: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): EngineOn |
| monitorFields | [androidJvm]<br>fun monitorFields(vararg fields: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Backbone.Retriever&lt;EngineOn&gt;<br>Monitor specific fields of the Backbone data for changes. By setting monitorFields the query becomes more specific and will only be notified of changes when the specific fields are modified. If monitorFields are not set then any change in the Backbone data will trigger a change. |

## Properties

| Name | Summary |
|---|---|
| fields | [androidJvm]<br>open override val fields: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>identifies specific fields in the Backbone data |
| key | [androidJvm]<br>open override val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>identifies the data in Backbone |