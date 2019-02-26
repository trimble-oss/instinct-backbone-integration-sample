# sample-app

A small sample application that demonstrates Trimble's Mobile Ecosystem Platform

## Getting Started

Login to https://trimbletransportation.jfrog.io/trimbletransportation with your Artifactory credentials that were supplied to you.

Once logged in, click your username in the upper right (https://trimbletransportation.jfrog.io/trimbletransportation/webapp/#/profile).

![alt text](doc/profile.png "Artifactory profile link")

Enter your password again (if required) and press unlock

![alt text](doc/unlock.png "Artifactory unlock button")

The API Key section should appear. Press the "Copy key to clipboard" button.

![alt text](doc/api-key.png "Artifactory api key")

Edit your [gradle.properties](https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_configuration_properties) file.
Gradle will look for that file in the following places:
* gradle.properties in project root directory.
* gradle.properties in `GRADLE_USER_HOME` directory.

If `GRADLE_USER_HOME` environment variable is not set, it will be defaulted to `USER_HOME/.gradle`. This is `C:\Users\<your user name>\.gradle` on Windows.

If you've never used Gradle before, you will have to create this file. You need to add 2 properties. The first, `tt_artifactory_username`, is the email address used to login to Artifactory. The second, `tt_artifactory_password`, is the long api key in your clipboard:

```gradle
...
# Automatically convert third-party libraries to use AndroidX
android.enableJetifier=true
# Kotlin code style for this project: "official" or "obsolete":
kotlin.code.style=official
# Trimble Artifactory credentials
tt_artifactory_username=michael_bayles@trimble.com
tt_artifactory_password=************
```

Inside the root [build.gradle](build.gradle), you'll see the reference to Artifactory:

```gradle
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

Inside [app/build.gradle](app/build.gradle), you'll see 2 dependencies that allow you to interact with the ecosystem:

```gradle
implementation 'com.trimble:ttm-mep-backbone-api:1.0'
implementation 'com.trimble:ttm-mep-backbone-logging:1.0'
```

If you've successfully authenticated with Artifactory, these dependencies should download upon a Gradle sync.