apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(Coroutines.coroutineLib)
    "implementation"(Glide.glideLibrary)

    // ********************************** Modules ************************************
    "implementation"(project(Modules.coreCommon))
    "implementation"(project(Modules.coreModels))
    "implementation"(project(Modules.coreNetwork))
    "implementation"(project(Modules.corePermission))
    "implementation"(project(Modules.coreUi))
    // ********************************** Modules ************************************
}
