![cover](https://github.com/devrath/Distance-Tracker/blob/main/Assets/Banner/banner.jpeg)


<img src="https://github.com/devrath/devrath/blob/master/images/kotlin_logo.png" align="right" title="Kotlin Logo" width="120">

# Distance-Tracker 🧞‍
[![Android Best practices](https://img.shields.io/badge/Android-best--practices-red)](https://www.android.com/intl/en_in/what-is-android/) [![Kotlin](https://img.shields.io/badge/Kotlin-1.6.10-brightgreen)](https://kotlinlang.org/) [![Coroutines](https://img.shields.io/badge/Coroutines-1.6.0-red)](https://kotlinlang.org/docs/reference/coroutines-overview.html) [![DaggerHilt](https://img.shields.io/badge/DaggerHilt-2.40-blue)](https://developer.android.com/training/dependency-injection/hilt-android) [![Firebase](https://img.shields.io/badge/Firebase-30.2.0-blueviolet)](https://firebase.google.com/) [![Timber](https://img.shields.io/badge/Timber-5.0.1-blue)](https://github.com/JakeWharton/timber) [![Orhanobut](https://img.shields.io/badge/orhanobut-2.2.0-lightgrey)](https://github.com/orhanobut/logger) [![Google Material](https://img.shields.io/badge/Google%20Material-1.4.0-3D3635)](https://material.io/develop/android/docs/getting-started) [![Crashlytics](https://img.shields.io/badge/Crashlytics-2.9.1-3B9C9C)](https://firebase.google.com/docs/crashlytics) [![Fused Location Service](https://img.shields.io/badge/Location-fused--location--service-orange)](https://developers.google.com/location-context/fused-location-provider)    


## **`𝙰𝚛𝚌𝚑𝚒𝚝𝚎𝚌𝚝𝚞𝚛𝚎`** 🎈
![Banner](https://github.com/devrath/Distance-Tracker/blob/main/Assets/Architecture/Architecture.png)

The architecture of the application is based, apply and strictly complies with each of the following 5 points:

-   A single-activity architecture, using the [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started) to manage fragment operations.
-   [Android architecture components](https://developer.android.com/topic/libraries/architecture/), part of Android Jetpack for give to project a robust design, testable and maintainable.
-   Pattern [Model-View-ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) (MVVM) facilitating a [separation](https://en.wikipedia.org/wiki/Separation_of_concerns) of development of the graphical user interface.
-   [S.O.L.I.D](https://en.wikipedia.org/wiki/SOLID) design principles intended to make software designs more understandable, flexible and maintainable.
-   [Modular app architecture](https://proandroiddev.com/build-a-modular-android-app-architecture-25342d99de82) allows to be developed features in isolation, independently from other features.

## **`𝙳𝚎𝚖𝚘 𝚘𝚏 𝚝𝚑𝚎 𝚙𝚛𝚘𝚓𝚎𝚌𝚝`** 💡
This application is used to track the user distance traveled and the time taken to travel the distance.

| **`Features with Description`** | **`Wiki`** | **`Demo`** |
| ------------------------------- | ---------- | ---------- |
| **`Location-Tracking`** : With this feature users can start the tracking from a source and travel to destination. Then measure the `distance travelled` and `time taken` for the journey with a visual display on the map using hte `polyline`. Users will be able to `track the distance` even when the app is not in the foreground or background using a `foreground service`. | [Documentation](https://github.com/devrath/Distance-Tracker/wiki/%F0%9D%99%BB%F0%9D%9A%98%F0%9D%9A%8C%F0%9D%9A%8A%F0%9D%9A%9D%F0%9D%9A%92%F0%9D%9A%98%F0%9D%9A%97-%F0%9D%9A%83%F0%9D%9A%9B%F0%9D%9A%8A%F0%9D%9A%8C%F0%9D%9A%94%F0%9D%9A%92%F0%9D%9A%97%F0%9D%9A%90-%F0%9F%A7%AD) | <img src="https://github.com/devrath/Distance-Tracker/blob/main/Assets/ScreenGif/Demo.gif" width="160" height="330"/> |
| [**`Splash API`**](https://developer.android.com/develop/ui/views/launch/splash-screen): With this API there is no need of creating a separate activity to customise the splash screen. The system will automatically create one for you. It also allows you to have a new launch animation for your apps and ability to perform a long running operation. |  |  |
| [**`In-App updates`**](https://developer.android.com/guide/playcore/in-app-updates): Using this API, users will be able to update the application in the background in an eligant way thus keeping the app always up-to-date. |  |  |
| [**`App-reviews`**](https://developer.android.com/guide/playcore/in-app-review): This API lets you prompt users to submit `Play Store ratings` and `reviews` without the inconvenience of leaving your app. |  |  |

### `𝙲𝚘𝚍𝚎 𝚜𝚝𝚢𝚕𝚎`🪀

To maintain the style and quality of the code, are used the bellow static analysis tools. All of them use properly configuration and you find them in the project root directory `.{toolName}`.

| Tools                                                   | Config file                                                                       | Check command             | Fix command               |
|---------------------------------------------------------|----------------------------------------------------------------------------------:|---------------------------|---------------------------|
| [detekt](https://github.com/arturbosch/detekt)          | [/.detekt](https://github.com/VMadalin/kotlin-sample-app/tree/master/.detekt)     | `./gradlew detekt`        | -                         |
| [ktlint](https://github.com/pinterest/ktlint)           | -                                                                                 | `./gradlew ktlint`        | `./gradlew ktlintFormat`  |
| [spotless](https://github.com/diffplug/spotless)        | [/.spotless](https://github.com/VMadalin/kotlin-sample-app/tree/master/.spotless) | `./gradlew spotlessCheck` | `./gradlew spotlessApply` |
| [lint](https://developer.android.com/studio/write/lint) | [/.lint](https://github.com/VMadalin/kotlin-sample-app/tree/master/.lint)         | `./gradlew lint`          | -                         |


## **`𝙲𝚘𝚗𝚝𝚛𝚒𝚋𝚞𝚝𝚎`** 🙋‍♂️
Read [contribution guidelines](CONTRIBUTING.md) for more information regarding contribution.

## **`𝙵𝚎𝚎𝚍𝚋𝚊𝚌𝚔`** ✍️ 
Feature requests are always welcome, [File an issue here](https://github.com/devrath/Distance-Tracker/issues/new).

## **`𝙵𝚒𝚗𝚍 𝚝𝚑𝚒𝚜 𝚙𝚛𝚘𝚓𝚎𝚌𝚝 𝚞𝚜𝚎𝚏𝚞𝚕`** ? ❤️
Support it by clicking the ⭐ button on the upper right of this page. ✌️

## **`𝙻𝚒𝚌𝚎𝚗𝚜𝚎`** ![Licence](https://img.shields.io/github/license/google/docsy) :credit_card:
This project is licensed under the Apache License 2.0 - see the [LICENSE](https://github.com/devrath/Distance-Tracker/blob/main/LICENSE) file for details


<p align="center">
<a><img src="https://forthebadge.com/images/badges/built-for-android.svg"></a>
</p>
