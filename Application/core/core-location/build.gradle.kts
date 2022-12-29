apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    //"implementation"(project(Modules.core))
    //"implementation"(Coroutines.coroutineLib)

    // ********************************** Map *****************************************
    "implementation"(Maps.playServicesLocation)
    "implementation"(Maps.androidMapsUtils)
    "implementation"(Maps.playServicesMaps)
    // ********************************** Map *****************************************
}