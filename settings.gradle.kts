pluginManagement {
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
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Deezer Player"
include(":app")
include(":core:resources")
include(":core:network")
include(":core:presentation")
include(":features:player")
include(":features:localtracks")
include(":features:deezertracks")
include(":data:localtracks")
include(":data:deezertracks")
include(":data:player")
include(":player:controller")
