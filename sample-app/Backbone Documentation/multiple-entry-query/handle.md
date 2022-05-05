# handle

[androidJvm]\
open fun handle(onEach: (MultipleEntryQuery.Result) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): Stoppable

Registers a callback that accepts a Result. Callback is executed on registration, when monitored data changes, or when the period elapses.

#### Return

Stoppable

## Parameters

androidJvm

| | |
|---|---|
| onEach | : callback for Result. |

[androidJvm]\
abstract fun handle(onEach: (MultipleEntryQuery.Result) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onError: ([Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), handler: [Handler](https://developer.android.com/reference/kotlin/android/os/Handler.html) = Handler()): Stoppable

Registers a callback that accepts a Result. Callback is executed on registration, when monitored data changes, or when the period elapses.

#### Return

Stoppable

## Parameters

androidJvm

| | |
|---|---|
| onEach | : callback for Result. |
| onError | : callback to handle Backbone fetch errors. |
| handler | : the Android Handler to run the query on. Default is the handler for the current thread. |