pluginManagement {
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

rootProject.name = "ElyraApps"

include(":ElyraSecurityCenter")
include(":ElyraPrivacyCenter")
include(":ElyraLauncher")
include(":ElyraFiles")
include(":ElyraUpdater")
include(":ElyraSettings")
