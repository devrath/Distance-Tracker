apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(Room.roomKtx)
    //"implementation"(Room.roomCompiler)
    "implementation"(Room.roomRuntime)

    // ********************************** Modules ************************************
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.corePreferences))
    "implementation"(project(Modules.coreCommon))
    // ********************************** Modules ************************************
}
