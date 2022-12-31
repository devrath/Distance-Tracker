apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(Coroutines.coroutineLib)
    "implementation"(Google.playCore)
    "implementation"(AndroidX.constraint)
    "implementation"(AndroidX.cardView)
    "implementation"(Lotte.lotteAnimation)
    "implementation"(AndroidX.fragment)
    "implementation"(AndroidX.fragmentKtx)

    // ********************************** Modules ************************************
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.corePreferences))
    "implementation"(project(Modules.coreCommon))
    // ********************************** Modules ************************************
}
