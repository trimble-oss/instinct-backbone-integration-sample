# SingleEntryQuery

[androidJvm]\
interface SingleEntryQuery&lt;out T : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;

Retrieve a single Backbone.Entry from Backbone

## Functions

| Name | Summary |
|---|---|
| alsoMonitor | [androidJvm]<br>abstract fun alsoMonitor](retriever: Backbone.Retriever&lt;*&gt;, vararg retrievers: Backbone.Retriever&lt;*&gt;): MultipleEntryQuery<br>Used to indicate more data should be retrieved and monitored for changes on Backbone. Calling alsoMonitor changes SingleEntryQuery into a MultipleEntryQuery. |
| alsoRetrieve | [androidJvm]<br>abstract fun alsoRetrieve(retriever: Backbone.Retriever&lt;*&gt;, vararg retrievers: Backbone.Retriever&lt;*&gt;): MultipleEntryQuery<br>Used to indicate more keys should be retrieved from Backbone. Calling alsoRetrieve changes the SingleKeyQuery into a MultipleKeyQuery. |
| every | [androidJvm]<br>abstract fun every(value: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), unit: [TimeUnit](https://developer.android.com/reference/kotlin/java/util/concurrent/TimeUnit.html)): SingleEntryQuery&lt;T&gt;<br>Used to indicate the period at which the query should be run. If the query is also monitoring data changes, then any data change will reset the period. |
| fetch | [androidJvm]<br>abstract fun fetch(): Backbone.Entry&lt;T&gt;?<br>Runs the query once. Any period or data monitoring will have no effect because the query is run only once. |
| handle | [androidJvm]<br>open fun handle(onEach: (Backbone.Entry&lt;T&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): Stoppable<br>abstract fun handle(onEach: (Backbone.Entry&lt;T&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onError: ([Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), handler: [Handler](https://developer.android.com/reference/kotlin/android/os/Handler.html) = Handler()): Stoppable<br>Registers a callback that accepts the Backbone.Entry type. The callback is executed on registration, when monitored data changes, or when the period elapses. |
| observe | [androidJvm]<br>abstract fun observe(): [Observable](http://reactivex.io/RxJava/javadoc/io/reactivex/Observable.html)&lt;out Backbone.Entry&lt;T&gt;&gt;<br>Creates an RxJava Observable that emits the Backbone.Entry. Available data is emitted on subscription, when monitored data changes, or when the period elapses. |