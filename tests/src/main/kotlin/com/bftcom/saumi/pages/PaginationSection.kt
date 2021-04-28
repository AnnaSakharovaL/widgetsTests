package pages

import com.bftcom.utils.*
import com.codeborne.selenide.Selectors.byTitle
import com.codeborne.selenide.SelenideElement
import io.qameta.allure.Step
import org.openqa.selenium.By

interface PaginationSectionInterface {
    var prevPageButton: HumanSelenideElement
    var nextPageButton: HumanSelenideElement
    var reloadPageButton: HumanSelenideElement
}

object Pagination : PageBuilder<PaginationSection>(PaginationSection::class)

class PaginationSection(block: SelenideElement? = null) : BaseBlock(
    if (block != null) {
        block.find(By.className("ant-table-pagination"))
    } else S(By.className("ant-table-pagination"))
), PaginationSectionInterface {

    override var prevPageButton: HumanSelenideElement
        @Step("Инициализация кнопки перехода к предыдущим страницам списка")
        get() = content.find(byTitle("Назад")).withTitle("Кнопка перехода к предыдущим страницам списка")
        set(prevPageButton) {
            this.prevPageButton = prevPageButton
        }

    override var nextPageButton: HumanSelenideElement
        @Step("Инициализация кнопки перехода к следующим страницам списка")
        get() = content.find(byTitle("Вперёд")).withTitle("Кнопка перехода к следующим страницам списка")
        set(nextPageButton) {
            this.nextPageButton = nextPageButton
        }

    override var reloadPageButton: HumanSelenideElement
        @Step("Инициализация кнопки обновления списка")
        get() = content.find(byTitle("Обновить")).withTitle("Кнопка обновления списка")
        set(reloadPageButton) {
            this.reloadPageButton = reloadPageButton
        }

    override fun toString(): String {
        return "Блок пейджинга"
    }
}

