package com.bftcom.saumi.pages

import com.bftcom.utils.*
import org.openqa.selenium.By

object RightMenu : PageBuilder<RightMenuBlock>(RightMenuBlock::class)

class RightMenuBlock : BaseBlock(By.className("right-main-menu")) {

    lateinit var rightMenu: HumanSelenideElement

    init {
        initBlock {
            rightMenu = content.find(By.className("ant-dropdown-trigger")).withTitle("для отображение Правого подменю")
        }
    }

    override fun toString(): String {
        return "Меню правого верхнего угла"
    }
}

object RightDropMenu : PageBuilder<RightDropMenuBlock>(RightDropMenuBlock::class)

class RightDropMenuBlock : BaseBlock(By.className("ant-dropdown-menu-root")) {

    lateinit var aboutSystemMenu: HumanSelenideElement
    lateinit var profileMenu: HumanSelenideElement
    lateinit var logoutMenu: HumanSelenideElement

    init {
        initBlock {
            aboutSystemMenu = content.findAll(By.className("ant-dropdown-menu-item"))[0].withTitle("меню О системе")
            profileMenu = content.findAll(By.className("ant-dropdown-menu-item"))[1].withTitle("меню Профиль")
            logoutMenu = content.findAll(By.className("ant-dropdown-menu-item"))[2].withTitle("меню Выход")
        }
    }

    override fun toString(): String {
        return "Выпадающее меню правого верхнего угла"
    }
}