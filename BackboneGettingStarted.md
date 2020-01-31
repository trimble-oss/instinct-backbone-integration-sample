# Getting Started

## Enabling Access To Trimble Artificatory

### Get Artifactory Api Key

If you don't have credentials to [https://trimbletransportation.jfrog.io/trimbletransportation](https://trimbletransportation.jfrog.io/trimbletransportation), send an email to ttm-jfrog-admin-ug@trimble.com to request them.

1. Login to [https://trimbletransportation.jfrog.io/trimbletransportation](https://trimbletransportation.jfrog.io/trimbletransportation) with your Artifactory credentials that were supplied to you. 

2. Click your username in the upper right:

![alt text](./img/profile.png "Artifactory profile link")

3. Unlock your profile (if required enter your password again):

![alt text](./img/unlock.png "Artifactory unlock button")

4. Copy your Api Key:

![alt text](./img/api-key.png "Artifactory api key")


### Set Artificatory Access in Gradle

In your Android projects root `build.gradle` add the Trimble Mobile Ecosystem maven repository:
```groovy
allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url = "https://trimbletransportation.jfrog.io/trimbletransportation/ttm-mvn-mobile-ecosystem"
            credentials {
                username = tt_artifactory_username
                password = tt_artifactory_password
            }
        }
    }
}
```

To keep your Artifactory Api Key secret you need to store it in your **user** [gradle.properties](https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_configuration_properties) file.

The default location is `USER_HOME/.gradle/gradle.properties` e.g. `C:\Users\<your user name>\.gradle\gradle.properties`.

Then add:
```groovy
tt_artifactory_username=michael_bayles@trimble.com
tt_artifactory_password=************
```

You can see this in action in the [sample app](https://github.com/PeopleNet/trimble-mobile-ecosystem-platform/blob/master/sample-app/build.gradle).

## Setting up the Dependencies

You need to include the Backbone API in your project, for example, as a Gradle dependency:
```groovy
implementation 'com.trimble:ttm-mep-backbone-api:version'
```

 Versions can be found on the [releases page](https://github.com/PeopleNet/trimble-mobile-ecosystem-platform/releases)

## Installing Prerequisite APKs

You will also need to install other platform applications onto your device for the Backbone to work. You can find them on the [releases page](https://github.com/PeopleNet/trimble-mobile-ecosystem-platform/releases).

## Tablet Setup

Follow [Tablet Setup Instructions](https://confluence.trimble.tools/pages/viewpage.action?spaceKey=MAINE&title=Android+Developers%3A+Get+Started+with+Instinct+Platform+Core+Apps+and+Libraries) before using your device to test your Backbone integration.