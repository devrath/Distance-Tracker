apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    // "implementation"(project(Modules.core))
    "implementation"(SplashScreen.splashAndroidApi)
    "implementation"(project(Modules.corePreferences))

}
