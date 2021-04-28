package com.bftcom.utils

import com.bftcom.saumi.pages.RightDropMenu
import com.bftcom.saumi.pages.RightMenu
import com.codeborne.selenide.Condition
import com.codeborne.selenide.SelenideElement
import com.github.romankh3.image.comparison.ImageComparison
import com.github.romankh3.image.comparison.model.ImageComparisonState
import io.qameta.allure.*
import org.openqa.selenium.OutputType
import pages.Login
import pages.MainMenu
import java.awt.image.BufferedImage
import java.awt.image.RenderedImage
import java.io.*
import javax.imageio.ImageIO

val sourceDir = "src/main/resources/"
val allureDir = "build/allure-results/"

@Attachment()
fun SelenideElement.isSamePatternLike(fileName: String): Boolean {

    waitUntil(Condition.visible, 5000)

    Thread.sleep(5000)
    this.scrollIntoView(true)

    val screen = getScreenshotAs(OutputType.FILE)
    val bufferedImg = ImageIO.read(screen)

    val filePattern = File("$sourceDir/$fileName.png")
    val bufferedPattern = ImageIO.read(filePattern)

    val comparison = ImageComparison(bufferedImg, bufferedPattern).compareImages()

    Allure.label("testType", "screenshotDiff")
    Allure.addAttachment("expected", ByteArrayInputStream(getScreenData(bufferedPattern)))
    Allure.addAttachment("actual", ByteArrayInputStream(getScreenData(bufferedImg)))
    Allure.addAttachment("diff", ByteArrayInputStream(getScreenData(comparison.result)))

    saveComparedFiles("test", bufferedImg, bufferedPattern, bufferedImg)

    return comparison.imageComparisonState == ImageComparisonState.MATCH
}

private fun saveComparedFiles(
    fileName: String,
    bufferedImg: BufferedImage?,
    bufferedPattern: BufferedImage?,
    bufferedDiff: BufferedImage?

) {
    val testFileName = fileName.withRandomPostfix()
    ImageIO.write(bufferedImg, "png", File("$allureDir$testFileName-test.png"))
    ImageIO.write(bufferedPattern, "png", File("$allureDir$testFileName-pattern.png"))
    ImageIO.write(bufferedDiff, "png", File("$allureDir$testFileName-diff.png"))
}

private fun getScreenData(image: RenderedImage): ByteArray {
    val scrArray = ByteArrayOutputStream()
    ImageIO.write(image, "png", scrArray)
    return scrArray.toByteArray()
}

@Step("Создание скриншота элемента")
fun makeElementScreenshot(element: SelenideElement, fileName: String) {

    element.waitUntil(Condition.visible, 5000)

    val screen = element.getScreenshotAs(OutputType.FILE)
    val bufferedImg = ImageIO.read(screen)
    ImageIO.write(bufferedImg, "png", File("src/main/resources/$fileName"))
}

@Step("Сравнение скриншотов")
fun compareScreenshots(fileFromTest1: String, fileFromTest2: String): Boolean {
    val file1 = File(fileFromTest1)
    val file2 = File(fileFromTest2)

    val bufferedFile1 = ImageIO.read(file1)
    val bufferedFile2 = ImageIO.read(file2)

    val comparation = ImageComparison(bufferedFile1, bufferedFile2)
    val comp = comparation.compareImages()

    return (comp.imageComparisonState.name == "MATCH")
}

@Step("Выход из системы")
fun logout() {
    if (RightMenu().rightMenu.isDisplayed) {
        MainMenu {
            RightMenu {
                rightMenu.openInList()
                RightDropMenu {
                    logoutMenu.openInList()
                }
            }
        }
    }
}

@Step("Проверка существования пользователя и вход в систему")
fun login(userLogin: String, userPassword: String) {
    checkAndCreateUser(userLogin, userPassword)
    if (Login().loginInput.isDisplayed) {
        Login {
            auth(userLogin, userPassword)
        }
    }
    if (RightMenu().rightMenu.isDisplayed) {
        if (RightMenu().rightMenu.text != userLogin) {
            logout()
            Login {
                auth(userLogin, userPassword)
            }
        }
    }
}