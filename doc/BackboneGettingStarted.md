# Getting Started

The library for integrating with the Instinct backbone library can be found under the Maven Central Repository:
```kotlin

buildscript {
    ext.kotlin_version = '1.9.22'
    repositories {
        google()
        mavenCentral()

    }
    
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}  
    

The library is referenced by the following Module's build.gradle file.
```kotlin
dependencies {
    ....
    implementation 'com.trimble:ttm-mep-backbone-api:7.3.0'
    ....
}
```

## Tablet Setup

It is currently required that hardware be supplied by Trimble to operate the sample application source code, so that it is integrated into the Instinct ecosystem.\
Contact your Trimble representive for getting the hardware needed to run this sample application.