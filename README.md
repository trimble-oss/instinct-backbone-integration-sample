# Trimble Mobile Ecosystem Platform

## Overview

Trimble Mobile Ecosystem Platform is a mobile platform built on Android that enables apps and services to use sensors and on-board computers, and provides a managed user experience.

The sensor and on-board computer data is exposed via a rich API called the Backbone.

## Getting Started

If you haven't configured your local environment to use the Trimble Transportation Artifactory server,
make sure to view the [Backbone Setup Guide](BackboneGettingStarted.md) before proceeding.

The next step is to include the Backbone API into your project, for example, as a Gradle dependency:

```groovy
implementation 'com.trimble:ttm-backbone-api:version'
```

Versions can be found on the [releases page](https://github.com/PeopleNet/trimble-mobile-ecosystem-platform/releases).

You will also need to install 2 applications onto your device for the Backbone to work. You can also find them on the [releases page](https://github.com/PeopleNet/trimble-mobile-ecosystem-platform/releases).

## Hello Backbone!

Create a Backbone object using the [BackboneFactory](https://maine-docs.dev-public.connectedfleet.io/com.trimble.ttm.backbone.api/-backbone-factory/) by passing in an Android Context:

```kotlin
val backbone = BackboneFactory.backbone(context)
val lastSpeed = backbone.fetch(ENGINE_SPEED_KMH_KEY)?.valueAs<Float>()
println("The last speed was $lastSpeed")
```

## Sample Application

There is a [sample app](https://github.com/PeopleNet/trimble-mobile-ecosystem-platform/tree/master/sample-app) available
that does a great job demonstrating a lot of the Backbone's functionality.

## Unit Testing

Creating unit tests for your application that interact with Backbone is possible via [mocking](https://en.wikipedia.org/wiki/Mock_object).

[Mockito](https://site.mockito.org/) and [Mockk](https://mockk.io) make this very easy. The [sample app](https://github.com/PeopleNet/trimble-mobile-ecosystem-platform/tree/master/sample-app) has some very good examples.
The main idea is to mock an instance of the Backbone and then have your mocked version return data you'd like to test against.
See [Unit Testing with the Backbone](BackboneUnitTesting.md) for some additional information.

## Additional Documentation

The automatically generated [KDocs](https://maine-docs.dev-public.connectedfleet.io/) and [Javadocs](https://maine-docs.dev-public.connectedfleet.io/javadoc) are also available.