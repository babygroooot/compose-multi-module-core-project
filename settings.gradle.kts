pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "multi-module-core-project"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include (":app")
include (":core")
include (":feature")
include (":core:network")
include (":core:domain")
include (":core:model")
include (":core:database")
include (":core:datastore")
include (":core:data")
include (":core:common")
include(":core:designsystem")
