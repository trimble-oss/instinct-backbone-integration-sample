# Trimble Mobile Ecosystem Platform

## Overview

Trimble Mobile Ecosystem Platform is a mobile platform built on Android that enables apps and services to use sensors and on-board computers, and provides a managed user experience.

The sensor and on-board computer data is exposed via a rich API called the Backbone.

## Getting Started

If you haven't configured your local environment to use the Trimble E-tools Artifactory server,
make sure to view the [Backbone Setup Guide](BackboneGettingStarted.md) before proceeding.

The next step is to include the Backbone API into your project, for example, as a Gradle dependency:

```groovy
implementation 'com.trimble:ttm-mep-backbone-api:version'
```

## Tablet Setup

Please follow the [Tablet Setup Instructions](TabletSetupInstructions.md).

## Sample Application

The sample app does a great job demonstrating a lot of the Backbone's functionality.

## Backbone Usage and Unit Testing

An in depth explanation of how to use backbone can be found in the [README](./sample-app/README.md) of the sample app. It will go over usage, and setup in a more depth manner, and provide examples.
Creating unit tests for your application that interact with Backbone is possible via [mocking](https://en.wikipedia.org/wiki/Mock_object).

[Mockito](https://site.mockito.org/) and [Mockk](https://mockk.io) make this very easy. The sample app has some very good examples.
The main idea is to mock an instance of the Backbone and then have your mocked version return data you'd like to test against.
See [Unit Testing with the Backbone](BackboneUnitTesting.md) for some additional information.

## Additional Documentation

The sample app includes links to help navigate through additional documentation like the kDocs for the backbone api.