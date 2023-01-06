# Mobile Automation Tests with Appium + Kotlin
---
## Objective
Executing mobile tests for iOS and Android locally or by device farm (BrowserStack as example)

---
## Tools
* Appium server installed
  https://appium.io/docs/en/commands/device/app/install-app/#appium-server

```
To check if appium is correctly installed run on terminal: appium-doctor --ios
```

* To run locally is necessary Android Studio or/and Xcode.
  https://developer.android.com/
  https://developer.apple.com/xcode/


* Test using BrowserStack is necessary upload your app.
  https://www.browserstack.com/docs/local-testing/releases-and-downloads
* Download the BrowserStack binay and add in the folder -> browserstack
```
GET APP List
curl --location --request GET 'https://api-cloud.browserstack.com/app-automate/recent_apps' \
--header 'Authorization: Basic <your basic auth from BS>'

ADD App
curl --location --request POST 'https://api-cloud.browserstack.com/app-automate/upload' \
--header 'Authorization: Basic <your basic auth from BS>' \
--form 'file=@"<your local file>' \
--form 'custom_id="<your custom name>"'

DELETE App
curl --location --request DELETE 'https://api-cloud.browserstack.com/app-automate/app/delete/56bd0096972369ab99efb76ca4e2ecebe7b3b7a6' \
--header 'Authorization: Basic <your basic auth from BS>'
```

```
-Dplatform=<android_bs>
-DbsUser=<bsUser>
-DbsPass=<bsKey>
-DplatformVersion=<15.0>
-DbuildVersion=<4.74>
-DdeviceName="<Pixel_4>"
```

* To check devices available locally:
 ```
 Android run in terminal: emulator -list-avds
 iOS run in terminal: xcrun simctl list
 ```

* Appium Inspector
  https://github.com/appium/appium-inspector/releases

```json
Capabilities to use in Appium Inspector example:

ANDROID
{
  "platformName": "Android",
  "appium:deviceName": "<Pixel_3_API_29>",
  "appium:app": "<apk path>",
  "appium:appPackage": "<app package>",
  "appium:appActivity": "<app activity>",
}

iOS
{
  "platformName": "IOS",
  "appium:deviceName": "<iPhone 13 Pro>",
  "appium:app": "<app path>",
  "appium:appPackage": "<app package>",
  "appium:appActivity": "<app activity>",
  "appium:platformVersion": "<15.0>"
}
```

By Thiago Maciel