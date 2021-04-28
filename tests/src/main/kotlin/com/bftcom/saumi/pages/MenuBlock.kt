package pages

import com.bftcom.saumi.pages.PopupMenu
import com.bftcom.utils.*
import com.codeborne.selenide.CollectionCondition
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.SelenideElement
import io.qameta.allure.Step
import org.openqa.selenium.By

object MainMenu : PageBuilder<MenuBlock>(MenuBlock::class)

class MenuBlock : BaseBlock(By.className("page-header")) {

    @Step("Перешли в раздел '{0}' -> '{1}'")
    fun go(mainMenuName: String, subMenuName: String? = null, secondLevelSubMenu: String? = null) {
        nowIs(visible)
        val mainMenuItem: SelenideElement
        mainMenuItem = mainMenu(mainMenuName)
        //if (mainMenuName == "···") {
        //    if (mainMenu("···").exists()) {
        //        mainMenuItem = mainMenu(mainMenuName)
        //    } else {
        //        mainMenuItem = mainMenu(subMenuName!!)
        //    }
        //} else {
        //    mainMenuItem = mainMenu(mainMenuName)
       // }
        if (subMenuName == null) {
            mainMenuItem.click()
        } else {
            mainMenuItem.hover()
            mainMenuItem.click() //Иногда без клика не открывается всплывающее меню
        }
        subMenuName?.let {
            if (secondLevelSubMenu == null) {
                PopupMenu {
                    subMenuInMainMenu(it).click()
                }
            } else {
                PopupMenu {
                    subMenuInMainMenu(it).hover().click()
                    waiting(500)
                }
            }
            //if (subMenuName == "Администрирование") {
            //    val widgetItem = subMenuInMainMenu("Виджеты")
            //    widgetItem.hover()
            //    widgetItem.click()
            //}
        }
        secondLevelSubMenu?.let {
            PopupMenu {
                subMenuInMainMenu(it).click()
            }
        }
    }

    private fun mainMenu(buttonTextOrTitle: String): SelenideElement {
        var menuItem: SelenideElement? = null
        val findAll = content.findAll("li")
        findAll.forEach {
            if (it.text.equals(buttonTextOrTitle)) {
                menuItem = it
            }
        }
        if (menuItem == null) {
            findAll.first{it.text.equals("···")}.hover()
            PopupMenu {
                menuItem = subMenuInMainMenu(buttonTextOrTitle)
            }

        }
        //findAll.shouldBe(CollectionCondition.sizeGreaterThan(1))
        //return findAll.first { it.text.equals(buttonTextOrTitle) }
        return menuItem!!
    }

    private fun subMenuInMainMenu(buttonTextOrTitle: String): SelenideElement {
        val subMenuBlock = S("div.ant-menu-submenu.ant-menu-submenu-popup.ant-menu-dark.ant-menu-submenu-placement-bottomLeft")
        subMenuBlock.shouldBe(visible)

        val buttons = subMenuBlock.findAll("li")
        buttons.shouldBe(CollectionCondition.sizeNotEqual(0))
        return buttons.first { it.text.equals(buttonTextOrTitle) }
    }

    override fun toString(): String {
        return "Главное меню"
    }
}

