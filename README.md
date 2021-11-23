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

Follow [Tablet Setup Instructions](https://confluence.trimble.tools/pages/viewpage.action?spaceKey=MAINE&title=Android+Developers%3A+Get+Started+with+Instinct+Platform+Core+Apps+and+Libraries) before using your device to test your Backbone integration.

## Sample Application

There is a [sample app](https://bitbucket.trimble.tools/projects/MAINE/repos/ttm-mep-sample-app/browse/sample-app) available
that does a great job demonstrating a lot of the Backbone's functionality.

## Backbone Getting Started

There is also a [Backbone Quick Start Page](https://confluence.trimble.tools/display/MAINE/How+to+Read+or+Write+Values+from+the+Backbone) available in confluence that can get you started right away.

## Unit Testing

Creating unit tests for your application that interact with Backbone is possible via [mocking](https://en.wikipedia.org/wiki/Mock_object).

[Mockito](https://site.mockito.org/) and [Mockk](https://mockk.io) make this very easy. The [sample app](https://bitbucket.trimble.tools/projects/MAINE/repos/ttm-mep-sample-app/browse/sample-app) has some very good examples.
The main idea is to mock an instance of the Backbone and then have your mocked version return data you'd like to test against.
See [Unit Testing with the Backbone](BackboneUnitTesting.md) for some additional information.

## Additional Documentation

The [sample app](https://bitbucket.trimble.tools/projects/MAINE/repos/ttm-mep-sample-app/browse/sample-app) includes links to help navigate through additional documentation like the [kDocs](https://bitbucket.trimble.tools/projects/MAINE/repos/ttm-mep-core-libraries/browse/backbone/api) for the backbone api.