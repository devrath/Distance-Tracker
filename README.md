<img src="https://github.com/devrath/devrath/blob/master/images/kotlin_logo.png" align="right" title="Kotlin Logo" width="120">

# Distance-Tracker 🧞‍

## **`𝙸𝚗𝚝𝚛𝚘𝚍𝚞𝚌𝚝𝚒𝚘𝚗`** 💡
This application is used to track the user distance traveled and the time taken to travel the distance


## **`𝙰𝚋𝚘𝚞𝚝 𝚙𝚎𝚛𝚖𝚒𝚜𝚜𝚒𝚘𝚗𝚜 𝚞𝚜𝚎𝚍 𝚏𝚘𝚛 𝚝𝚑𝚎 𝚊𝚙𝚙`** 💡
▪️ In android sharing user location is very delicate so a series of permissions need to be provided from user </br>
▪️ There are two scenarios to handle when it comes to runtime permissions `ACCESS_FINE_LOCATION`,`ACCESS_BACKGROUND_LOCATION`</br>


```xml
<!--
DESCRIPTION: -> The permission ACCESS_FINE_LOCATION is needed to get the user's location from the device
RUNTIME-PERMISSION: -> Needed
-->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<!--
DESCRIPTION: -> The permission ACCESS_BACKGROUND_LOCATION is needed from android-10 and above in older version its by default provided
RUNTIME-PERMISSION: -> Needed
-->
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
<!--
DESCRIPTION: -> The permission FOREGROUND_SERVICE is needed to display the foreground notification as a service
RUNTIME-PERMISSION: -> Not needed
-->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
```


## Set-Up 👣

🔰 `Steps for creating a debug key for map-API`
```java
* Get the package name from your manifest
* Get the SHA-1 certificate fingerprint from your application(You can use GradleTasks -> android -> siginingReport)
* There is a useful link to generate the SHA-1 key(https://stackoverflow.com/a/68728675/1083093)
* Place the SHA Keys: https://console.cloud.google.com/google/maps-apis/credentials?project=distance-tracker-369716
* Enable google maps SDK for Android and get the key, Useful link(https://developers.google.com/maps/documentation/android/start#get-key)
* Then add a string resource(SECRET.xml) in project location SRC -> DEBUG -> RES -> VALUES -> SECRET.XML and the key
* <resources><string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">dddsdjbeioudhqweuhd8</string></resources>
```   

🔰 `Steps for creating a release key for map-API`
```java
* Generate a keystore file by entering the proper credentials
* Use the keystore file to generate the SHA-1 key 
* COMMAND➜ keytool -list -v -keystore /Users/devrath/keys/Keystore -alias AliasName
* Useful link:➜ How to get API key: https://developers.google.com/maps/documentation/android/start#get-key 
* Place the SHA Keys: https://console.cloud.google.com/google/maps-apis/credentials?project=distance-tracker-369716
* Then add a string resource(SECRET.xml) in project location SRC -> RELEASE -> RES -> VALUES -> SECRET.XML and the key
* <resources><string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">SHJJS8s8shhdsdssdss</string></resources>
```  

🔰 `Create firebase account`
```java
* Since the project uses firebase to register crashes we need to create a firebase account.
* Place the google-services.json in the project app level.
```  



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
