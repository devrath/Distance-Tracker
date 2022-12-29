object Build {
    private const val androidBuildToolsVersion = "7.3.0"
    const val androidBuildTools = "com.android.tools.build:gradle:$androidBuildToolsVersion"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
    const val kotlinStdlibPlugin = "org.jetbrains.kotlin:kotlin-stdlib:${Kotlin.version}"
    const val kotlinAndroidPlugin = "org.jetbrains.kotlin.android:${Kotlin.version}"

    private const val hiltAndroidGradlePluginVersion = "2.42"
    const val hiltAndroidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltAndroidGradlePluginVersion"

    private const val googleServicesVersion = "4.3.13"
    const val googleServicesGradlePlugin = "com.google.gms:google-services:$googleServicesVersion"

    private const val crashlyticsVersion = "2.9.1"
    const val crashlyticsPlugin = "com.google.firebase:firebase-crashlytics-gradle:$crashlyticsVersion"

    private const val ktLintVersion = "10.3.0"
    const val ktLintPlugin = "org.jlleitschuh.gradle:ktlint-gradle:$ktLintVersion"

    private const val navSafeArgsVersion = "2.5.3"
    const val navSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$navSafeArgsVersion"

    private const val secretsGradleVersion = "2.0.1"
    const val secretsGradle = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:$secretsGradleVersion"

    private const val spotlessVersion = "6.12.0"
    const val spotlessPlugin = "com.diffplug.spotless:spotless-plugin-gradle:$spotlessVersion"


    object BuildPlugins {
        const val androidLibrary = "com.android.library"
        const val androidApplication = "com.android.application"

        const val kotlinAndroid = "kotlin-android"
        const val kotlinNavigationSafeargs = "androidx.navigation.safeargs.kotlin"
        const val kotlinParcelize = "kotlin-parcelize"
        const val kotlinKapt = "kotlin-kapt"
        const val daggerHiltAndroidPlugin = "dagger.hilt.android.plugin"
        const val mapsplatformSecretsGradlePlugin = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin"

        const val googleServices = "com.google.gms.google-services"
        const val crashlytics = "com.google.firebase.crashlytics"
        const val ktLint = "org.jlleitschuh.gradle.ktlint"
        const val spotless = "com.diffplug.spotless"

        const val kotlinAndroidExtensions = "kotlin-android-extensions"
    }

    object BuildModule {
        const val kotlinAndroid = "android"
    }
}