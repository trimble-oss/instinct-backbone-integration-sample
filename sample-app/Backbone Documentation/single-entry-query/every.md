# every

[androidJvm]\
abstract fun every(value: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), unit: [TimeUnit](https://developer.android.com/reference/kotlin/java/util/concurrent/TimeUnit.html)): SingleEntryQuery&lt;T&gt;

Used to indicate the period at which the query should be run. If the query is also monitoring data changes, then any data change will reset the period.

#### Return

Updated query