package com.bftcom.saumi.pages

import com.bftcom.utils.*
import org.openqa.selenium.By

object Notification : PageBuilder<AntNotification>(AntNotification::class)

class AntNotification : BaseBlock(By.className("ant-notification")) {
    lateinit var notificationBlock: HumanSelenideElement

    init {
        initBlock {
            notificationBlock = content.find(By.className("ant-notification-notice")).withTitle("блок отображения оповещения")
        }
    }

    override fun toString(): String {
        return "Компонент оповещений"
    }
}