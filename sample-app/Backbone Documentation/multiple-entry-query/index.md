# MultipleEntryQuery

[androidJvm]\
interface MultipleEntryQuery

Retrieve multiple Backbone.Entries from Backbone

## Types

| Name | Summary |
|---|---|
| Result | [androidJvm]<br>interface Result : [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)&lt;[Backbone.Entry](../-backbone/-entry/index.md)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;?&gt; <br>Result contains the data requested in a MultipleEntryQuery |

## Functions

| Name | Summary |
|---|---|
| alsoMonitor | [androidJvm]<br>abstract fun alsoMonitor(retriever: Backbone.Retriever&lt;*&gt;, vararg retrievers: Backbone.Retriever&lt;*&gt;): MultipleEntryQuery<br>Used to indicate more keys should be retrieved and monitored for changes on Backbone |
| alsoRetrieve | [androidJvm]<br>abstract fun alsoRetrieve](also-retrieve.md)(retriever: Backbone.Retriever&lt;*&gt;, vararg retrievers: Backbone.Retriever&lt;*&gt;): MultipleEntryQuery<br>Used to indicate more keys should be retrieved from Backbone |
| every | [androidJvm]<br>abstract fun every(value: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), unit: [TimeUnit](https://developer.android.com/reference/kotlin/java/util/concurrent/TimeUnit.html)): MultipleEntryQuery<br>Used to indicate the period at which the query should be run. If the query is also monitoring data changes, then any data change will reset the period. |
| fetch | [androidJvm]<br>abstract fun fetch(): MultipleEntryQuery.Result<br>Runs the query once. Any period or data monitoring will have no effect because the query is run only once. |
| handle | [androidJvm]<br>open fun handle(onEach: (MultipleEntryQuery.Result) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): Stoppable<br>abstract fun handle(onEach: (MultipleEntryQuery.Result) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onError: ([Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), handler: [Handler](https://developer.android.com/reference/kotlin/android/os/Handler.html) = Handler()): Stoppable<br>Registers a callback that accepts a Result. Callback is executed on registration, when monitored data changes, or when the period elapses. |
| observe | [androidJvm]<br>abstract fun observe(): [Observable](http://reactivex.io/RxJava/javadoc/io/reactivex/Observable.html)&lt;[MultipleEntryQuery.Result](-result/index.md)&gt;<br>Creates an RxJava Observable that emits Result. Values are emitted on subscription, when monitored data changes, or when the period elapses. |