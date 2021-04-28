package com.bftcom.saumi.pages

import com.bftcom.utils.*
import com.codeborne.selenide.Condition
import com.codeborne.selenide.SelenideElement
import org.openqa.selenium.By

object PopupMenu : PageBuilder<SubMenuPopup>(SubMenuPopup::class)

class SubMenuPopup : BaseBlock(SS(By.className("ant-menu-submenu-popup")).last()) {

    fun subMenuInMainMenu(buttonTextOrTitle: String): SelenideElement {
        content.waitUntil(Condition.appear, 7000)
        val findAll = content.findAll("li")
        return findAll.first { it.text.equals(buttonTextOrTitle) }
    }
}