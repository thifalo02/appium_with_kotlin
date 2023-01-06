package br.thiago.core

import org.junit.jupiter.api.Test
import java.util.logging.Logger

class AppiumServer {
    private val log: Logger = Logger.getLogger(Test::class.java.name)
    private val utils = Utils()

    fun startAppiumServer() {
        log.info("Starting appium local server")
        utils.startAppiumLocally()
        Thread.sleep(3000)
        log.info("Appium local server is running")
    }

    fun stopAppiumServer() {
        log.info("Stopping appium local server")
        utils.executeCommandLine("pkill -9 -f appium")
        log.info("Appium local server is stopped")
    }
}