apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    // "implementation"(project(Modules.core))
    "implementation"(Retrofit.gsonConverter)
    "implementation"(Room.roomKtx)
    "implementation"(Room.roomRuntime)
    "implementation"(Coroutines.coroutineLib)
    "implementation"(KotlinSerialization.kotlinSerializationJson)
    "implementation"(project(Modules.coreCommon))
    //"implementation"(Retrofit.okHttp)
    //"implementation"(Retrofit.okHttpLoggingInterceptor)
}
