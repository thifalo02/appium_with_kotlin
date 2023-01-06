package br.thiago.commons

import br.thiago.core.StartAppium
import io.appium.java_client.MobileElement
import io.appium.java_client.TouchAction
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.PointOption
import org.openqa.selenium.Dimension
import org.openqa.selenium.NoAlertPresentException
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class Actions : StartAppium() {

    private lateinit var wait: WebDriverWait

    fun acceptPopUp() {
        try {
            driver?.switchTo()?.alert()?.accept()
        } catch (_: NoAlertPresentException) {
        }
    }

    fun waitTobeClicked(element: MobileElement) {
        wait = WebDriverWait(driver, 60)
        waitVisibilityOf(element)
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)))
    }

    fun waitVisibilityOf(element: MobileElement) {
        wait = WebDriverWait(driver, 60)
        try {
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)))
            element.isDisplayed
        } catch (e: Exception) {
            Thread.sleep(500)
        }
    }

    fun waitVisibilityOf(element: MobileElement, seconds: Long) {
        wait = WebDriverWait(driver, seconds)
        try {
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)))
            element.isDisplayed
        } catch (e: Exception) {
            Thread.sleep(500)
        }
    }

    fun isVisible(element: MobileElement, seconds: Long): Boolean {
        wait = WebDriverWait(driver, seconds)
        return try {
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)))
            element.isDisplayed
        } catch (e: Exception) {
            false
        }
    }

    fun click(element: MobileElement) {
        waitTobeClicked(element)
        element.click()
    }

    fun sendKeys(element: MobileElement, text: String) {
        click(element)
        element.sendKeys(text)
    }

    fun clear(element: MobileElement) {
        waitTobeClicked(element)
        element.clear()
    }

    fun getText(element: MobileElement): String {
        waitTobeClicked(element)
        return element.text
    }

    fun isDisplayed(element: MobileElement): Boolean {
        return element.isDisplayed
    }

    fun scrollBetween(startPercentualPoint: Double, endPercentualPoint: Double) {
        val size: Dimension = (driver!!.manage().window().size) as Dimension
        val xAxis = size.width / 2
        val startPointAtYAxis = (size.height * startPercentualPoint).toInt()
        val endPointAtYAxis = (size.height * endPercentualPoint).toInt()
        TouchAction(driver)
            .press(PointOption.point(xAxis, startPointAtYAxis))
            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
            .moveTo(PointOption.point(xAxis, endPointAtYAxis))
            .release()
            .perform()
    }
}