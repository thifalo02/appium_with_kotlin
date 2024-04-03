package br.thiago.commons

import br.thiago.core.StartAppium
import org.openqa.selenium.NoAlertPresentException
import org.openqa.selenium.WebElement
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

    fun waitTobeClicked(element: WebElement) {
        wait = WebDriverWait(driver, Duration.ofSeconds(60))
        waitVisibilityOf(element)
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)))
    }

    fun waitVisibilityOf(element: WebElement) {
        wait = WebDriverWait(driver, Duration.ofSeconds(60))
        try {
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)))
            element.isDisplayed
        } catch (e: Exception) {
            Thread.sleep(500)
        }
    }

    fun waitVisibilityOf(element: WebElement, seconds: Long) {
        wait = WebDriverWait(driver, Duration.ofSeconds(seconds))
        try {
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)))
            element.isDisplayed
        } catch (e: Exception) {
            Thread.sleep(500)
        }
    }

    fun isVisible(element: WebElement, seconds: Long): Boolean {
        wait = WebDriverWait(driver, Duration.ofSeconds(seconds))
        return try {
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)))
            element.isDisplayed
        } catch (e: Exception) {
            false
        }
    }

    fun click(element: WebElement) {
        waitTobeClicked(element)
        element.click()
    }

    fun sendKeys(element: WebElement, text: String) {
        click(element)
        element.sendKeys(text)
    }

    fun clear(element: WebElement) {
        waitTobeClicked(element)
        element.clear()
    }

    fun getText(element: WebElement): String {
        waitTobeClicked(element)
        return element.text
    }

    fun isDisplayed(element: WebElement): Boolean {
        return element.isDisplayed
    }
}