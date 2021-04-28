package pages

import com.bftcom.utils.*
import com.codeborne.selenide.SelenideElement
import io.qameta.allure.Step
import org.openqa.selenium.By

interface ActionsInterface {
    var addButton: HumanSelenideElement
}

object Actions : PageBuilder<ActionsSection>(ActionsSection::class)

class ActionsSection(block: SelenideElement? = null) : BaseBlock(
    if (block != null) {
        block.find(By.className("page-header-actions"))
    } else {
        S(By.className("page-header-actions"))
    }
), ActionsInterface {

    override var addButton: HumanSelenideElement
        @Step("Инициализация кнопки добавления новой записи")
        get() = content.find(Button("Добавить")).withTitle("Добавить новую запись")
        set(addButton) {
            this.addButton = addButton
        }

    //override var addButton = content.find(By.className("add-action")).withTitle("Добавить новую запись")

    override fun toString(): String {
        return "Блок действий"
    }
}

