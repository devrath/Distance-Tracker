plugins {
    id(Build.BuildPlugins.androidApplication)
    kotlin(Build.BuildModule.kotlinAndroid)
    id(Build.BuildPlugins.daggerHiltAndroidPlugin)
    id(Build.BuildPlugins.kotlinKapt)
    id(Build.BuildPlugins.googleServices)
    id(Build.BuildPlugins.crashlytics)
    id(Build.BuildPlugins.ktLint)
    id(Build.BuildPlugins.kotlinParcelize)
    id(Build.BuildPlugins.kotlinNavigationSafeargs)
    id(Build.BuildPlugins.mapsplatformSecretsGradlePlugin)
    id("java-test-fixtures")
}

android {
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        // testInstrumentationRunner = ProjectConfig.testRunner
        testInstrumentationRunner = "com.iprayforggod.HiltTestRunner"
        vectorDrawables { useSupportLibrary = true }
    }

    /** ************************* FLAVOURS ************************* **/
    buildTypes {
        getByName(FlavourUtils.BuildTypes.DEBUG) {
            isMinifyEnabled = false
            // applicationIdSuffix = ".${FlavourUtils.BuildTypes.DEBUG}"
            isDebuggable = true
        }
        getByName(FlavourUtils.BuildTypes.RELEASE) {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    flavorDimensions.add(FlavourUtils.FlavorDimensions.DEFAULT)
    productFlavors {
        create(FlavourUtils.ProductFlavors.DEV) {
            dimension = FlavourUtils.FlavorDimensions.DEFAULT
            // applicationIdSuffix = ".${FlavourUtils.ProductFlavors.DEV}"
            // versionNameSuffix = "-${FlavourUtils.ProductFlavors.DEV}"
        }
        create(FlavourUtils.ProductFlavors.INTERNAL) {
            dimension = FlavourUtils.FlavorDimensions.DEFAULT
            // applicationIdSuffix = ".${FlavourUtils.ProductFlavors.INTERNAL}"
            // versionNameSuffix = "-${FlavourUtils.ProductFlavors.INTERNAL}"
        }
        create(FlavourUtils.ProductFlavors.PUBLIC) {
            dimension = FlavourUtils.FlavorDimensions.DEFAULT
        }
    }
    /** ************************* FLAVOURS ************************* **/

    kotlinOptions { jvmTarget = ProjectConfig.jvmTarget }
    buildFeatures { viewBinding = true }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
        resources.excludes.add("**/attach_hotspot_windows.dll")
        resources.excludes.add("META-INF/licenses/ASM")
    }
}

dependencies {
    implementation(platform(Firebase.googleFirebase))
    implementation(Firebase.firebaseKtx)
    implementation(Firebase.firebaseDatabaseKtx)
    implementation(Firebase.firebaseAnalytics)
    implementation(Firebase.firebaseCrashlyticsKtx)
    implementation(Firebase.firebaseAnalyticsKtx)

    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)

    implementation(Google.material)

    implementation(SplashScreen.splashAndroidApi)

    implementation(LifeCycle.lifecycleExtensions)
    implementation(Permission.lifecycleExtensions)
    implementation(Maps.playServicesLocation)
    implementation(Maps.androidMapsUtils)
    implementation(Maps.playServicesMaps)
    implementation(Navigation.navigationFragmentKtx)
    implementation(Navigation.navigationUiKtx)

    kapt(Room.roomCompiler)
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)

    testImplementation(Testing.junit4)
    testImplementation(Testing.junitAndroidExt)
    testImplementation(Testing.truth)
    testImplementation(Testing.coroutines)
    testImplementation(Testing.turbine)
    testImplementation(Testing.mockk)
    testImplementation(Testing.mockWebServer)
    testImplementation(Testing.unitCoreTesting)
    testImplementation(Testing.unitRoomTesting)
    testImplementation(Testing.unitCore)
    testImplementation(Testing.unitMockitoKotlin)
    testImplementation(Testing.mockitoInline)
    testImplementation(Testing.mockitoCore)
    testImplementation(Testing.orgJson)
    testImplementation(Testing.roboElectric)

    androidTestImplementation(Testing.junit4)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.truth)
    androidTestImplementation(Testing.coroutines)
    androidTestImplementation(Testing.turbine)
    androidTestImplementation(Testing.mockk)
    androidTestImplementation(Testing.mockWebServer)
    androidTestImplementation(Testing.hiltTesting)

    kaptAndroidTest(DaggerHilt.hiltCompiler)
    androidTestImplementation(Testing.testRunner)

    // Injecting fakes
    //androidTestImplementation(project(Modules.coreMock))
}
