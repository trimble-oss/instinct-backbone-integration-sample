# alsoMonitor

[androidJvm]\
abstract fun alsoMonitor(retriever: Backbone.Retriever&lt;*&gt;, vararg retrievers: Backbone.Retriever&lt;*&gt;): MultipleEntryQuery

Used to indicate more data should be retrieved and monitored for changes on Backbone. Calling alsoMonitor changes SingleEntryQuery into a MultipleEntryQuery.

#### Return

MultipleEntryQuery with all previously set parameters