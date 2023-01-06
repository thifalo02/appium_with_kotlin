package br.thiago.core

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import io.restassured.RestAssured
import org.junit.jupiter.api.Test
import java.util.logging.Logger

class BrowserStack {
    private val log: Logger = Logger.getLogger(Test::class.java.name)
    private val utils = Utils()
    private val bsUser: String = System.getProperty("bsUser")
    private val bsPass: String = System.getProperty("bsPass")

    fun startBrowserStackServer() {
        log.info("Starting Browserstack Local Server")
        val os = utils.getSystemOperation()
        val path = "./src/test/resources/browserstack"
        with(os) {
            when {
                contains(
                    "mac",
                    true
                ) -> utils.executeCommandLine("$path/mac/BrowserStackLocal --key $bsPass")
                contains(
                    "windows",
                    true
                ) -> utils.executeCommandLine("$path/windows/BrowserStackLocal.exe    --key $bsPass")
                else -> utils.executeCommandLine("$path/linux/BrowserStackLocal --key $bsPass")
            }
            log.info("Executing tests on browserstack...")
        }
    }

    fun stopBrowserStackServer() {
        val os: String = utils.getSystemOperation()
        log.info("Stopping Browserstack Local Server")
        if (!os.contains("windows", true)) {
            utils.executeCommandLine("killall BrowserStackLocal")
        } else {
            utils.executeCommandLine("tskill BrowserStackLocal")
        }
    }

    private fun getDeviceList(): String {
        val endpoint = "https://api-cloud.browserstack.com/app-automate/devices.json"
        return RestAssured.given()
            .auth()
            .preemptive()
            .basic(bsUser, bsPass)
            .`when`()
            .get(endpoint)
            .then()
            .extract()
            .body()
            .jsonPath()
            .prettify()
    }

    fun getDevice(os: String = "android", os_version: String = "", device: String = ""): BsGetDeviceModel {
        val deviceList = getDeviceList()
        val filteredDevices = mutableListOf<BsGetDeviceModel>()
        val modelType = object : TypeToken<List<BsGetDeviceModel>>() {}.type

        Gson().fromJson<List<BsGetDeviceModel>>(deviceList, modelType).forEach {
            if (it.os == os && it.os_version.contains(os_version) && it.device.contains(device)) {
                filteredDevices.add(it)
            }
        }
        val index = if (filteredDevices.size > 1) utils.generateRandom(filteredDevices.size.minus(1)) else 0
        return filteredDevices[index]
    }
}