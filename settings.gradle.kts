rootProject.name = "ByEChinaApp"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// nd-kpm-base: base library for MVI, domain, and data layers
// Provides: NDViewModel, NDScreen, NDUseCase, NDResult, NDRepository, NDDispatcherProvider
includeBuild("base")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":app:androidApp")
include(":app:shared")
include(":core")