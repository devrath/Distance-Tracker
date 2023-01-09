apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    // "implementation"(project(Modules.core))
    "implementation"(Retrofit.gsonConverter)
    //"implementation"(Retrofit.okHttp)
    //"implementation"(Retrofit.okHttpLoggingInterceptor)
}
