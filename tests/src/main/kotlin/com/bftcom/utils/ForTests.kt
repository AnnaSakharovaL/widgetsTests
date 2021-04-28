package com.bftcom.utils

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.WebDriverRunner
import com.codeborne.selenide.logevents.SelenideLogger
import io.qameta.allure.Attachment
import io.qameta.allure.Step
import io.qameta.allure.selenide.AllureSelenide
import org.junit.jupiter.api.*
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import pages.Login
import pages.MainMenu
import utils.PropertyLoader

open class ForTests {

    /*@BeforeClass
    open fun setUp() {
        initConfiguration()
        SelenideLogger.addListener("allure", AllureSelenide())
        openUrl("/")
    }*/

    /*@AfterEach
    fun tearDown() {
        MainMenu {
            go("Рабочие панели", "Главная страница")
        }
        //SelenideLogger.removeAllListeners()
        //close()
    }*/

    @BeforeEach
    open fun setUp() {
        SelenideLogger.addListener("allure", AllureSelenide())
        openUrl("/")
    }

    @AfterEach
    fun tearDown() {
        SelenideLogger.removeAllListeners()
    }

    @Attachment(value = "Screenshot", type = "image/png")
    fun captureScreenshot(): ByteArray {
        val screenshot = (WebDriverRunner.getWebDriver() as TakesScreenshot).getScreenshotAs(OutputType.BYTES);
        return screenshot
    }

    companion object {
        @BeforeAll
        @JvmStatic
        internal fun setUpForClass() {
            initConfiguration()
            setUpMainUsagePages()
        }

        private fun initConfiguration() {
            //------------Конфигурация для локального запуска
            Configuration.baseUrl = PropertyLoader.loadProperty("baseURL")
            Configuration.browser = "chrome"
            Configuration.startMaximized = true

            //------------Конфигурация для удаленного запуска
            //Configuration.baseUrl = "https://k8s-st.bftcom.com:8600/saumi3/standat/"
            //Configuration.browser = "utils.SelenoidWebDriverProvider"
            //Configuration.startMaximized = true

            Configuration.timeout = 10000;
            Configuration.reopenBrowserOnFail = true
        }

        @Step("Инициализация часто используемых компонентов страниц")
        private fun setUpMainUsagePages() {
            Login {}
            MainMenu {}
        }

        @AfterAll
        @JvmStatic
        internal fun tearDown() {
            //for (i in 2..14) {
            //    deleteUser("root$i")
            // }
            //Selenide.close()
        }
    }

}