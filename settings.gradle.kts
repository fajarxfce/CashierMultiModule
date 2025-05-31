pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
    }
}
rootProject.name = "fajar-tampan"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

check(JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_17)) {
    """
    Now in Android requires JDK 17+ but it is currently using JDK ${JavaVersion.current()}.
    Java Home: [${System.getProperty("java.home")}]
    https://developer.android.com/build/jdks#jdk-config-in-studio
    """.trimIndent()
}

include(":app")
include(":core:common")
include(":core:model")
include(":core:datastore")
include(":core:datastore-proto")
include(":core:data")
include(":core:domain")
include(":core:network")
include(":navigation")
include(":core:ui")
include(":feature")
include(":feature:home:ui")
include(":feature:home:domain")
include(":feature:splash:ui")
include(":feature:onboarding:ui")
include(":feature:login:ui")
include(":feature:pos:ui")
include(":feature:login:domain")
include(":feature:login:data")
include(":feature:splash:data")
include(":feature:splash:domain")
include(":feature:pos:domain")
include(":feature:pos:data")
include(":feature:transaction-history:ui")
include(":core:database")
include(":feature:pos:db")
include(":feature:cart:ui")
include(":feature:cart:domain")
include(":feature:cart:data")
include(":feature:transaction-history:domain")
include(":feature:transaction-history:data")
