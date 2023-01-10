apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    // "implementation"(project(Modules.core))
    "implementation"(Room.roomKtx)
    "api"(Room.roomRuntime)
    "kapt"(Room.roomCompiler)
    "implementation"(Coroutines.coroutineLib)
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.coreModels))
    "implementation"(project(Modules.coreCommon))
}
