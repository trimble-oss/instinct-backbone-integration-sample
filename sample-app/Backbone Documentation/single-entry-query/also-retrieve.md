# alsoRetrieve

[androidJvm]\
abstract fun alsoRetrieve(retriever: Backbone.Retriever&lt;*&gt;, vararg retrievers: Backbone.Retriever&lt;*&gt;): MultipleEntryQuery

Used to indicate more keys should be retrieved from Backbone. Calling alsoRetrieve changes the SingleKeyQuery into a MultipleKeyQuery.

#### Return

MultipleEntryQuery with all previously set parameters