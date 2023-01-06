package br.thiago.core

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.remote.MobileCapabilityType
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

open class StartAppium {

    companion object {
        var driver: AppiumDriver<MobileElement>? = null
    }

    private val browserStack: BrowserStack = BrowserStack()
    private val appiumServer: AppiumServer = AppiumServer()
    private val utils: Utils = Utils()

    @BeforeEach
    fun startDriver() {
        val bsUser = System.getProperty("bsUser")
        val bsPass = System.getProperty("bsPass")
        val path = System.getProperty("user.dir")
        val libPath = "/mobile/src/test/resources/app"
        val platform = System.getProperty("platform") ?: throw Exception("O Sistema Operacional não foi informado!")
        val build = System.getProperty("buildVersion")
        val url = URL("http://127.0.0.1:4723/wd/hub")
        val platformVersion = System.getProperty("platformVersion")
        val deviceName = System.getProperty("deviceName")
        val timeout = 90L

        when {
            platform.equals("android", true) -> {
                appiumServer.startAppiumServer()
                utils.startDeviceLocal(deviceName)

                DesiredCapabilities().apply {
                    setCapability(MobileCapabilityType.DEVICE_NAME, deviceName)
                    setCapability(MobileCapabilityType.PLATFORM_NAME, platform)
                    setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2")
//                    setCapability(MobileCapabilityType.APP, "$path$libPath")
                    setCapability("appPackage", "com.android.calculator2")
                    setCapability("appActivity", "com.android.calculator2.Calculator")
                    setCapability("autoGrantPermissions", true)
                    setCapability("newCommandTimeout", 180)
                }.also {
                    driver = AndroidDriver(url, it)
                }
            }

            platform.equals("android_bs", true) -> {
                browserStack.startBrowserStackServer()
                val osVersion = System.getProperty("osVersion") ?: ""
                val device = browserStack.getDevice(device = deviceName, os_version = osVersion)

                DesiredCapabilities().apply {
                    setCapability("browserstack.user", bsUser)
                    setCapability("browserstack.key", bsPass)
                    setCapability("app", "emission")
                    setCapability("device", device.device)
                    setCapability("os_version", device.os_version)
                    setCapability("browserstack.appiumLogs", "true")
                    setCapability("browserstack.debug", "true")
                    setCapability("browserstack.local", "true")
                    setCapability("browserstack.networkLogs", "true")
                    setCapability("browserstack.idleTimeout", timeout)
                    setCapability("project", "T-Maciel Appium Tests")
                    setCapability("build", "Android - $build")
                    setCapability("appPackage", "")
                    setCapability("appActivity", "")
                    setCapability("autoGrantPermissions", true)
                    setCapability("newCommandTimeout", 180)
                }.also {
                    driver = AndroidDriver(URL("https://$bsUser:$bsPass@hub-cloud.browserstack.com/wd/hub"), it)
                }
            }

            platform.equals("ios", true) -> {
                appiumServer.startAppiumServer()
                utils.startDeviceLocal(deviceName)

                DesiredCapabilities().apply {
                    setCapability(MobileCapabilityType.DEVICE_NAME, deviceName)
                    setCapability(MobileCapabilityType.PLATFORM_NAME, platform)
                    setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion)
                    setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest")
                    setCapability(MobileCapabilityType.APP, utils.getIosAppLocalPath())
                    setCapability("autoGrantPermissions", true)
                    setCapability("newCommandTimeout", 180)
                }.also {
                    driver = IOSDriver(url, it)
                }
            }

            platform.equals("ios_bs", true) -> {
                browserStack.startBrowserStackServer()
                val osVersion = System.getProperty("osVersion") ?: ""
                val device = browserStack.getDevice("ios", osVersion, deviceName)

                DesiredCapabilities().apply {
                    setCapability("browserstack.user", bsUser)
                    setCapability("browserstack.key", bsPass)
                    setCapability("app", "")
                    setCapability("device", device.device)
                    setCapability("os_version", device.os_version)
                    setCapability("browserstack.appiumLogs", "true")
                    setCapability("browserstack.debug", "true")
                    setCapability("browserstack.local", "true")
                    setCapability("browserstack.networkLogs", "true")
                    setCapability("browserstack.idleTimeout", timeout)
                    setCapability("project", "T-Maciel Appium Tests")
                    setCapability("build", "iOS - $build")
                    setCapability("autoGrantPermissions", true)
                    setCapability("newCommandTimeout", 180)
                }.also {
                    driver = IOSDriver(URL("https://$bsUser:$bsPass@hub-cloud.browserstack.com/wd/hub"), it)
                }
            }
        }
    }

    @AfterEach
    fun tearDown() {
        if (System.getProperty("platform").contains("_bs")) {
            browserStack.stopBrowserStackServer()
        } else {
            driver?.quit()
            appiumServer.stopAppiumServer()
            utils.stopDeviceLocal(System.getProperty("deviceName"))
        }
    }
}