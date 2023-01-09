apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    // "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreModels))
    "implementation"(Retrofit.retrofit)
    "implementation"(Retrofit.gsonConverter)
    "implementation"(Retrofit.okHttp)
    "implementation"(Retrofit.okHttpLoggingInterceptor)
}
