// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.hiltAndroidGradlePlugin)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.googleServicesGradlePlugin)
        classpath(Build.crashlyticsPlugin)
        classpath(Build.ktLintPlugin)
        classpath(Build.navSafeArgs)
        classpath(Build.secretsGradle)
        classpath(Build.spotlessPlugin)
        classpath(kotlin(Build.gradlePlugin, version = Kotlin.version))
        classpath(kotlin(Build.kotlinSerialization, version = Kotlin.version))
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

subprojects {
    apply(plugin = Build.BuildPlugins.ktLint) // Version should be inherited from parent
    apply(plugin = Build.BuildPlugins.spotless) // Version should be inherited from parent

    repositories {
        google()
        mavenCentral()
        // Required to download KtLint
        maven("https://plugins.gradle.org/m2/")
    }

    // Optionally configure plugin
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> { debug.set(true) }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}