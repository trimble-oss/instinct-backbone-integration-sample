# Result

[androidJvm]\
interface Result : [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)&lt;Backbone.Entry&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;?&gt;

Result contains the data requested in a MultipleEntryQuery

## Functions

| Name | Summary |
|---|---|
| forEach | [androidJvm]<br>open fun forEach(p0: [Consumer](https://developer.android.com/reference/kotlin/java/util/function/Consumer.html)&lt;in Backbone.Entry&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;?&gt;) |
| get | [androidJvm]<br>abstract operator fun &lt;T : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; get(retriever: Backbone.Retriever&lt;T&gt;): Backbone.Entry&lt;T&gt;?<br>Returns the data that corresponds to the given Retriever. If Backbone did not contain any data for a given Retriever then null is returned. |
| iterator | [androidJvm]<br>abstract operator override fun iterator(): [Iterator](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterator/index.html)&lt;[Backbone.Entry](../../-backbone/-entry/index.md)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;?&gt;<br>Iterates over all values queried from Backbone. The data field for each Backbone.Entry is the JSON stored in Backbone. If Backbone did not contain any data for a given Retriever the value will be null. |
| spliterator | [androidJvm]<br>open fun spliterator(): [Spliterator](https://developer.android.com/reference/kotlin/java/util/Spliterator.html)&lt;[Backbone.Entry](../../-backbone/-entry/index.md)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;?&gt; |