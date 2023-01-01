apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    // ********************************** Modules ************************************
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.corePreferences))
    "implementation"(project(Modules.coreCommon))
    "implementation"(project(Modules.coreDatabase))
    // ********************************** Modules ************************************
}