apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    // "implementation"(project(Modules.core))


    "implementation"(Room.roomKtx)
    //"implementation"(Room.roomCompiler)
    "implementation"(Room.roomRuntime)
    "implementation"(Coroutines.coroutineLib)
    

    "implementation"(project(Modules.coreUi))
}
