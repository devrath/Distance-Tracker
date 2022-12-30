apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.corePreferences))
    "implementation"(project(Modules.coreCommon))
    "implementation"(Coroutines.coroutineLib)
    "implementation"(Google.playCore)
    "implementation"(AndroidX.constraint)
    "implementation"(AndroidX.cardView)
    "implementation"(Lotte.lotteAnimation)
    "implementation"(AndroidX.fragment)
    "implementation"(AndroidX.fragmentKtx)

    // ********************************** Modules ************************************
    "implementation"(project(Modules.coreUi))
    // ********************************** Modules ************************************
}
