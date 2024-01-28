pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven {
            url = uri("https://jcenter.bintray.com")
            maven { url = uri("https://www.jitpack.io" ) }
        }
        jcenter()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven {
            url = uri("https://jcenter.bintray.com")
            maven { url = uri("https://www.jitpack.io" ) }
        }
        jcenter()
    }
}

rootProject.name = "Typing Challenge"
include(":app")
