apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.coreCommon))
    "implementation"(project(Modules.coreUi))
    "implementation"(Permission.customPermission)
}
