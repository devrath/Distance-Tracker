![cover](https://github.com/devrath/Distance-Tracker/blob/main/Assets/Banner/banner.jpeg)



<a href="https://play.google.com/store/apps/details?id=com.istudio.distancetracker"><img src="https://github.com/devrath/Distance-Tracker/blob/main/Assets/Images/google-play-store.png" align="right"></a>


# Distance-Tracker 🧞‍
[![Android Best practices](https://img.shields.io/badge/Android-best--practices-red)](https://www.android.com/intl/en_in/what-is-android/) [![Kotlin](https://img.shields.io/badge/Kotlin-1.6.10-brightgreen)](https://kotlinlang.org/) [![Coroutines](https://img.shields.io/badge/Coroutines-1.6.0-red)](https://kotlinlang.org/docs/reference/coroutines-overview.html) [![DaggerHilt](https://img.shields.io/badge/DaggerHilt-2.40-blue)](https://developer.android.com/training/dependency-injection/hilt-android) [![Firebase](https://img.shields.io/badge/Firebase-30.2.0-blueviolet)](https://firebase.google.com/) [![Timber](https://img.shields.io/badge/Timber-5.0.1-blue)](https://github.com/JakeWharton/timber) [![Orhanobut](https://img.shields.io/badge/orhanobut-2.2.0-lightgrey)](https://github.com/orhanobut/logger) [![Google Material](https://img.shields.io/badge/Google%20Material-1.4.0-3D3635)](https://material.io/develop/android/docs/getting-started) [![Crashlytics](https://img.shields.io/badge/Crashlytics-2.9.1-3B9C9C)](https://firebase.google.com/docs/crashlytics) [![Fused Location Service](https://img.shields.io/badge/Location-fused--location--service-orange)](https://developers.google.com/location-context/fused-location-provider)    


## **`𝙰𝚛𝚌𝚑𝚒𝚝𝚎𝚌𝚝𝚞𝚛𝚎`** 🎈
![Banner](https://github.com/devrath/Distance-Tracker/blob/main/Assets/Architecture/Architecture.png)

The architecture of the application is based, apply and strictly complies with each of the following 5 points:

| `SlNo` | `Description` |
| ------ | ------------- |
|   `1`  | A single-activity architecture, using the [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started) to manage fragment operations. |
|   `2`  | [Android architecture components](https://developer.android.com/topic/libraries/architecture/), part of Android Jetpack for give to project a robust design, testable and maintainable. |
|   `3`  | Pattern [Model-View-ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) (MVVM) facilitating a [separation](https://en.wikipedia.org/wiki/Separation_of_concerns) of development of the graphical user interface. |
|   `4`  | [S.O.L.I.D](https://en.wikipedia.org/wiki/SOLID) design principles intended to make software designs more understandable, flexible and maintainable. |
|   `5`  | [Modular app architecture](https://proandroiddev.com/build-a-modular-android-app-architecture-25342d99de82) allows to be developed features in isolation, independently from other features. |



## **`𝙳𝚎𝚖𝚘 𝚘𝚏 𝚝𝚑𝚎 𝚙𝚛𝚘𝚓𝚎𝚌𝚝`** 💡
This application is used to track the user distance traveled and the time taken to travel the distance.

| **`Features with Description`** | **`Wiki`** | **`Demo`** |
| ------------------------------- | ---------- | ---------- |
| **`Location-Tracking`** : With this feature users can start the tracking from a source and travel to destination. Then measure the `distance travelled` and `time taken` for the journey with a visual display on the map using hte `polyline`. Users will be able to `track the distance` even when the app is not in the foreground or background using a `foreground service`. | [Documentation](https://github.com/devrath/Distance-Tracker/wiki/%F0%9D%99%BB%F0%9D%9A%98%F0%9D%9A%8C%F0%9D%9A%8A%F0%9D%9A%9D%F0%9D%9A%92%F0%9D%9A%98%F0%9D%9A%97-%F0%9D%9A%83%F0%9D%9A%9B%F0%9D%9A%8A%F0%9D%9A%8C%F0%9D%9A%94%F0%9D%9A%92%F0%9D%9A%97%F0%9D%9A%90-%F0%9F%A7%AD) | <img src="https://github.com/devrath/Distance-Tracker/blob/main/Assets/ScreenGif/Demo.gif" width="160" height="330"/> |
| [**`Splash API`**](https://developer.android.com/develop/ui/views/launch/splash-screen): With this API there is no need of creating a separate activity to customise the splash screen. The system will automatically create one for you. It also allows you to have a new launch animation for your apps and ability to perform a long running operation. |  |  |
| [**`In-App updates`**](https://developer.android.com/guide/playcore/in-app-updates): Using this API, users will be able to update the application in the background in an eligant way thus keeping the app always up-to-date. |  |  |
| [**`App-reviews`**](https://developer.android.com/guide/playcore/in-app-review): This API lets you prompt users to submit `Play Store ratings` and `reviews` without the inconvenience of leaving your app. |  |  |
| **`Network Observer`**: This is used to observe the network change if connected/disconnected to user | [Documentation](https://github.com/devrath/droid-network-observer) | <img src="https://github.com/devrath/Distance-Tracker/blob/main/Assets/ScreenGif/network_observer.gif" width="160" height="330"/> |
| **`Toggling dark/light mode from app`**: This application supports system dark/light mode along with it user can force the toggling of modes in android |  | <img src="https://github.com/devrath/Distance-Tracker/blob/main/Assets/ScreenGif/dark_light_mode.gif" width="160" height="330"/> |
| **`Changing the map style`**: We can dynamically change the map style of android to provide a different user experience |  | <img src="https://github.com/devrath/Distance-Tracker/blob/main/Assets/ScreenGif/switch_map_types.gif" width="160" height="330"/> |

### `𝙳𝚎𝚙𝚎𝚗𝚍𝚎𝚗𝚌𝚒𝚎𝚜`🧵

* [Jetpack-Androidx](https://developer.android.com/jetpack/androidx): Major improvement to the original Android Support Library, which is no longer maintained.
* [View-Binding](https://developer.android.com/topic/libraries/view-binding): Allows you to more easily write code that interacts with views. An instance of a binding class contains direct references to all views that have an ID in the corresponding layout.
* [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle): Perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata): lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services.
* [Navigation](https://developer.android.com/guide/navigation/): helps you implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer.
* [Timber](https://github.com/JakeWharton/timber): a logger with a small, extensible API which provides utility on top of Android's normal Log class.
* [DataStore](https://developer.android.com/topic/libraries/architecture/datastore?gclid=CjwKCAiAkrWdBhBkEiwAZ9cdcJQtRe-hDXI0aPKf4u8n0EfU-xh1ODfQ80ImOVEPKgRHaVmwGCrb-xoCJQwQAvD_BwE&gclsrc=aw.ds): iData storage solution that allows you to store key-value pairs.
* [Fused location provider](https://developers.google.com/location-context/fused-location-provider): Get location data for your app based on combined signals from the device sensors using a battery-efficient API.
* [Google play core](https://developer.android.com/guide/playcore): Download additional language resources, Manage delivery of feature modules, Manage delivery of asset packs, Trigger in-app updates, Request in-app reviews
* [Coroutines](https://developer.android.com/kotlin/coroutines?gclid=CjwKCAiAkrWdBhBkEiwAZ9cdcD5t8r7gogrF9MOUjglqJc0sx55z13IfZldSaHt8Wm1c2qLg7gwSlBoCKq8QAvD_BwE&gclsrc=aw.ds): A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously



### `𝙲𝚘𝚍𝚎 𝚜𝚝𝚢𝚕𝚎`🪀

To maintain the style and quality of the code, are used the bellow static analysis tools. 

| Tools                                                   | Config file                                                                       | Check command             | Fix command               |
|---------------------------------------------------------|----------------------------------------------------------------------------------:|---------------------------|---------------------------|
| [detekt](https://github.com/arturbosch/detekt)          | [/.detekt](https://github.com/VMadalin/kotlin-sample-app/tree/master/.detekt)     | `./gradlew detekt`        | -                         |
| [ktlint](https://github.com/pinterest/ktlint)           | -                                                                                 | `./gradlew ktlint`        | `./gradlew ktlintFormat`  |
| [spotless](https://github.com/diffplug/spotless)        | [/.spotless](https://github.com/VMadalin/kotlin-sample-app/tree/master/.spotless) | `./gradlew spotlessCheck` | `./gradlew spotlessApply` |
| [lint](https://developer.android.com/studio/write/lint) | [/.lint](https://github.com/VMadalin/kotlin-sample-app/tree/master/.lint)         | `./gradlew lint`          | -                         |

## **`𝚃𝚎𝚌𝚑 𝚜𝚝𝚊𝚌𝚔`** 🏗️️ 

| What            | How                        |
|----------------	|------------------------------	|
| 🎭 𝚄𝚜𝚎𝚛 𝙸𝚗𝚝𝚎𝚛𝚏𝚊𝚌𝚎 (𝙰𝚗𝚍𝚛𝚘𝚒𝚍)   | [𝙹𝚎𝚝𝚙𝚊𝚌𝚔 𝙲𝚘𝚖𝚙𝚘𝚜𝚎](https://developer.android.com/jetpack/compose) , [𝙰𝚌𝚝𝚒𝚟𝚒𝚝𝚢+𝚇𝙼𝙻](https://developer.android.com/develop/ui/views/layout/declaring-layout)               |
| 🏗 𝙰𝚛𝚌𝚑𝚒𝚝𝚎𝚌𝚝𝚞𝚛𝚎    | [𝙲𝚕𝚎𝚊𝚗 𝙰𝚛𝚌𝚑𝚒𝚝𝚎𝚌𝚝𝚞𝚛𝚎](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)                            |
| 💉 𝙳𝙸 (𝙰𝚗𝚍𝚛𝚘𝚒𝚍)                | [𝙷𝚒𝚕𝚝](https://developer.android.com/training/dependency-injection/hilt-android)                        |
| 🌊 𝙰𝚜𝚢𝚗𝚌            | [𝙲𝚘𝚛𝚘𝚞𝚝𝚒𝚗𝚎𝚜](https://kotlinlang.org/docs/coroutines-overview.html) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/)                |
| 🌐 𝙳𝚊𝚝𝚊𝚋𝚊𝚜𝚎        | [𝚁𝚘𝚘𝚖](https://developer.android.com/jetpack/androidx/releases/room?gclid=Cj0KCQiAtbqdBhDvARIsAGYnXBN8WeUfoN8Ln5XfcNlF83mZZbJjiboM1DU95jgGawDZ5pfegpNQWOMaAtt0EALw_wcB&gclsrc=aw.ds)                       |

## **`𝙿𝚕𝚊𝚢𝚜𝚝𝚘𝚛𝚎 𝙳𝚘𝚠𝚗𝚕𝚘𝚊𝚍`** 🧰
<a href="https://play.google.com/store/apps/details?id=com.istudio.distancetracker"><img src="https://github.com/devrath/Distance-Tracker/blob/main/Assets/Images/google-play.png" height="75"></a>

## **`𝚂𝚞𝚙𝚙𝚘𝚛𝚝`** ☕
If you feel like support me a coffee for my efforts, I would greatly appreciate it.</br>
<a href="https://www.buymeacoffee.com/devrath" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/yellow_img.png" alt="Buy Me A Coffee" style="height: 41px !important;width: 174px !important;box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;-webkit-box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;" ></a>

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
