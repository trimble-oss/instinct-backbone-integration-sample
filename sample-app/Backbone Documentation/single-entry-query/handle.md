# handle

[androidJvm]\
open fun handle(onEach: (Backbone.Entry&lt;T&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): Stoppable

Registers a callback that accepts the Backbone.Entry type. The callback is executed on registration, when monitored data changes, or when the period elapses.

#### Return

Stoppable

## Parameters

androidJvm

| | |
|---|---|
| onEach | : callback for designated type. |

[androidJvm]\
abstract fun handle(onEach: (Backbone.Entry&lt;T&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onError: ([Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), handler: [Handler](https://developer.android.com/reference/kotlin/android/os/Handler.html) = Handler()): Stoppable

Registers a callback that accepts the Backbone.Entry type. The callback is executed on registration, when monitored data changes, or when the period elapses.

#### Return

Stoppable

## Parameters

androidJvm

| | |
|---|---|
| onEach | : callback for designated type. |
| onError | : callback to handle Backbone fetch errors. |
| handler | : the Android Handler to run the query on. Default is the handler for the current thread. |