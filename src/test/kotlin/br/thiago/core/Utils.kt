package br.thiago.core

import org.apache.commons.exec.CommandLine
import org.apache.commons.exec.DaemonExecutor
import org.apache.commons.exec.DefaultExecuteResultHandler
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.file.Paths
import java.util.*
import java.util.logging.Logger

class Utils {

    private val log: Logger = Logger.getLogger(Test::class.java.name)

    fun executeCommandLine(command: String): BufferedReader {
        val process: Process = if (getSystemOperation().contains("windows", true)) {
            Runtime.getRuntime().exec(arrayOf("powershell", "-l", "-c", command))
        } else {
            val runtime = Runtime.getRuntime()
            runtime.exec("/bin/zsh -l -c")
            runtime.exec(command)
        }
        BufferedReader(InputStreamReader(process.inputStream)).also {
            return it
        }
    }

    fun startAppiumLocally() {
        val cmdline = CommandLine("appium")
        cmdline.addArgument("--log-level")
        cmdline.addArgument("info:error")
        val resultHandler = DefaultExecuteResultHandler()
        val daemon = DaemonExecutor()

        daemon.execute(cmdline, resultHandler)
    }

    fun getSystemOperation(): String {
        return System.getProperty("os.name")
    }

    fun generateRandom(end: Int): Int {
        return Random().nextInt(end)
    }

    fun startDeviceLocal(deviceName: String) {
        log.info("Starting device local - $deviceName")
        if (System.getProperty("platform").equals("android", true)) {
            executeCommandLine("emulator -avd $deviceName")
            Thread.sleep(10000)
        } else {
            executeCommandLine("xcrun simctl boot \"$deviceName\"")
            Thread.sleep(3000)
            executeCommandLine("open -a Simulator")
            Thread.sleep(10000)
        }
        log.info("Device $deviceName is loaded")
    }

    fun stopDeviceLocal(deviceName: String) {
        log.info("Stopping device local - $deviceName")
        if (System.getProperty("platform").equals("android", true)) {
            executeCommandLine("adb emu kill")
        } else {
            executeCommandLine("killall iOS Simulator")
        }
        log.info("Device $deviceName is stopped")
    }

    fun getDeviceName(): String {
        val p: Process = Runtime.getRuntime().exec("adb shell getprop | ro.boot.serialno")
        val bf = BufferedReader(InputStreamReader(p.inputStream))
        return bf.readLine().trim().replace("[ro.boot.serialno]: [", "").replace("]", "")
    }

    fun getIosAppLocalPath(): String {
        val userDerivedDataPath: String = System.getProperty("user.home") + "/Library/Developer/Xcode/DerivedData/"
        val psMyAccountPath: String =
            Paths.get(userDerivedDataPath).toFile().list()?.filter { a -> a.contains("Application-") }?.get(0) ?: "???"
        val appPath = "/Build/Products/QA-iphonesimulator/Application.app"

        return userDerivedDataPath + psMyAccountPath + appPath
    }
}