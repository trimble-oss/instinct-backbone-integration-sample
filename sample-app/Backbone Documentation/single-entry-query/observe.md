# observe

[androidJvm]\
abstract fun observe(): [Observable](http://reactivex.io/RxJava/javadoc/io/reactivex/Observable.html)&lt;out Backbone.Entry&lt;T&gt;&gt;

Creates an RxJava Observable that emits the Backbone.Entry. Available data is emitted on subscription, when monitored data changes, or when the period elapses.

#### Return

RxJava Observable