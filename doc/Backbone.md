# Trimble Mobile Ecosystem Data Backbone

The Backbone stores data so other Android Apps can access it when needed.

* [Getting started](BackboneGettingStarted.md)
How to add the Backbone Api to your project
* Backbone API Flavors
  * [Backbone](VanillaBackbone.md)
  Provides methods to synchronously fetch information from Backbone
  * [Callback Backbone](CallbackBackbone.md)
  Provides all the methods provided by [Backbone](VanillaBackbone.md) and provides methods for asynchronous fetches.
  * [Rx Backbone](RxBackbone.md)
  Provides all the methods provided by [Backbone](VanillaBackbone.md) and provides methods for asynchronous fetches that return RxJava2 Observables.
* [Data Types](BackboneDataTypes.md)
The keys and types of data stored in Backbone.
* [Result Classes](BackboneResult.md)
The classes returned by [Backbone](VanillaBackbone.md) fetch methods.
