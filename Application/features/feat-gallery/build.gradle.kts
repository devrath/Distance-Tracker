apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(Coroutines.coroutineLib)

    // ********************************** Modules ************************************
    "implementation"(project(Modules.coreCommon))
    "implementation"(project(Modules.coreModels))
    "implementation"(project(Modules.coreNetwork))
    "implementation"(project(Modules.corePermission))
    // ********************************** Modules ************************************
}
