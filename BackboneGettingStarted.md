# Getting Started

The library for integrating with the Instinct backbone library can be found under the following folder within the sample app:
```kotlin
...\ttm-mep-sample-app\sample-app\app\libs
```

The library is referenced by the following Module's build.gradle file.
```kotlin
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar', '*.aar'])
```

## Tablet Setup

It is currently required that hardware be supplied by Trimble to operate the sample application source code, so that it is integrated into the Instinct ecosystem.
Contact your Trimble contact for getting hardware to run this sample application.