// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    `version-catalog`
    `maven-publish`
    alias(libs.plugins.kotlin.android) version libs.versions.kotlin apply false
    alias(libs.plugins.android.application) version libs.versions.agp apply false
    alias(libs.plugins.ksp) version libs.versions.ksp apply false
    alias(libs.plugins.kotlin.serialization) version libs.versions.kotlin apply false
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}
