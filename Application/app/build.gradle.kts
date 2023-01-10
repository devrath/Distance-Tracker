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

// CODE-QUALITY:-> Spotless
apply {
    from("$rootDir/buildSrc/scripts/spotless.gradle")
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
        }
        create(FlavourUtils.ProductFlavors.PUBLIC) {
            dimension = FlavourUtils.FlavorDimensions.DEFAULT
            // signingConfig =
        }
    }
    /** ************************* FLAVOURS ************************* **/

    kotlinOptions { jvmTarget = ProjectConfig.jvmTarget }
    buildFeatures { compose = true }
    buildFeatures { viewBinding = true }
    composeOptions { kotlinCompilerExtensionVersion = Compose.composeVersion }
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

    // ********************************** Compose ************************************
    implementation(Compose.compiler)
    implementation(Compose.ui)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.hiltNavigationCompose)
    implementation(Compose.material)
    implementation(Compose.runtime)
    implementation(Compose.navigation)
    implementation(Compose.viewModelCompose)
    implementation(Compose.activityCompose)
    implementation(Compose.uiToolingPreview)
    implementation(project(mapOf("path" to ":core:core-models")))
    debugImplementation(Compose.uiTooling)
    // ********************************** Compose ************************************

    // ********************************** Firebase ************************************
    implementation(platform(Firebase.googleFirebase))
    implementation(Firebase.firebaseAnalyticsKtx)
    implementation(Firebase.firebaseKtx)
    // ********************************** Firebase ************************************

    // ********************************** Basic ***************************************
    implementation(SplashScreen.splashAndroidApi)
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(Google.material)
    implementation(LifeCycle.lifecycleExtensions)
    implementation(LifeCycle.lifecycleLiveData)
    implementation(Navigation.navigationFragmentKtx)
    implementation(Navigation.navigationUiKtx)
    implementation(DataStorePreferences.datastorePreferences)
    // implementation(Moshi.moshiRepo)
    implementation(Gson.gson)
    implementation(CustomLogging.orhanobut)
    implementation(CustomLogging.timber)
    implementation(Coroutines.coroutineAndroidLib)
    implementation(Coroutines.coroutineLib)
    implementation(Coroutines.coroutinePlayServicesLib)
    implementation(Lotte.lotteAnimation)
    implementation(Google.playCore)
    kapt(Moshi.moshiCodegen)
    // ********************************** Basic ***************************************

    // ********************************** Database ************************************
    kapt(Room.roomCompiler)
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)
    // ********************************** Database ************************************

    // ********************************** Hilt ****************************************
    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)
    // ********************************** Hilt ****************************************

    // ********************************** Map *****************************************
    implementation(Maps.playServicesLocation)
    implementation(Maps.androidMapsUtils)
    implementation(Maps.playServicesMaps)
    // ********************************** Map *****************************************

    // ***************************** Unit Testing *************************************
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
    // ***************************** Unit Testing *************************************

    // ************************* Instrumentation Testing ******************************
    androidTestImplementation(Testing.junit4)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.truth)
    androidTestImplementation(Testing.coroutines)
    androidTestImplementation(Testing.turbine)
    androidTestImplementation(Testing.mockk)
    androidTestImplementation(Testing.mockWebServer)
    androidTestImplementation(Testing.hiltTesting)
    androidTestImplementation(Testing.testRunner)
    kaptAndroidTest(DaggerHilt.hiltCompiler)
    // ************************* Instrumentation Testing ******************************

    // ********************************** Modules ************************************
    implementation(project(Modules.featInAppReview))
    implementation(project(Modules.featRepository))
    implementation(project(Modules.coreCommon))
    implementation(project(Modules.coreLogger))
    implementation(project(Modules.coreLocation))
    implementation(project(Modules.coreConnectivity))
    implementation(project(Modules.corePreferences))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.corePermission))
    implementation(project(Modules.coreNetwork))
    implementation(project(Modules.coreModels))
    // ********************************** Modules ************************************
}
