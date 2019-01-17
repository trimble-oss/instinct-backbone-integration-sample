import java.net.URI

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:3.3.0-rc02")
        classpath(kotlin("gradle-plugin", version = "1.3.11"))
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url = URI.create("https://artifactory.trimble.tools/artifactory/ttm-mobile-ecosystem-maven")
            credentials {
                val artifactory_user: String by project
                val artifactory_password: String by project
                username = artifactory_user
                password = artifactory_password
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}