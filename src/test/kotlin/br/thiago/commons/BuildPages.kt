package br.thiago.commons

import br.thiago.core.StartAppium.Companion.driver
import io.appium.java_client.pagefactory.AppiumFieldDecorator
import org.openqa.selenium.support.PageFactory

abstract class BuildPages {
    init {
        PageFactory.initElements(AppiumFieldDecorator(driver), this)
    }
}