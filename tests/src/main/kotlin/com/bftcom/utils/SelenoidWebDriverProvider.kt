package utils

import com.codeborne.selenide.WebDriverProvider
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URI

/**
 * Провайдер для селеноида. Можно сделать дополнительную настройку через переменные среды
 */
class SelenoidWebDriverProvider : WebDriverProvider {
    override fun createDriver(capabilities: DesiredCapabilities): WebDriver {
        val browser = DesiredCapabilities().apply {
            browserName = "chrome"
            version = "latest"
            setCapability("enableVNC", false)
            //setCapability("applicationCacheEnabled", true)
        }

        try {
            val url = URI.create("http://test:123456@srv-at-selenoid:8888/wd/hub").toURL()

            return RemoteWebDriver(url, browser).apply {
                manage().window().size = Dimension(1280, 1024)
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}