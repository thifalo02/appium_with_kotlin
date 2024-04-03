package br.thiago.core

import java.io.FileInputStream
import java.util.Properties

class Properties {
    companion object {
        val props: Properties

        init {
            val file = FileInputStream(System.getProperty("user.dir") + "/appium.properties")
            props = Properties()
            props.load(file)
        }
    }
}