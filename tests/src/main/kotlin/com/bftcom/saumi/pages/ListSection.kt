package pages

import com.bftcom.utils.*
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selectors.*
import com.codeborne.selenide.SelenideElement
import io.qameta.allure.Step
import org.openqa.selenium.By

interface ListTableInterface {
    var list: HumanSelenideElement

    fun delete(cellValue: String, listBlock: SelenideElement)

    fun approve(value: String)

    fun openInList(cellValue: String, content: SelenideElement)

    fun openInModalList(cellValue: String, content: SelenideElement)

    fun openInField(content: SelenideElement, linkText: String)

    fun findItemByText(text: String, content: SelenideElement, countTry: Int = 0): ExpandSelenideElement?

    fun findItemInModal(elementValue: String, content: SelenideElement): SelenideElement?

    fun findMessageByText(text: String): SelenideElement

    fun findInList(text: String): Boolean
}

object List : PageBuilder<ListSection>(ListSection::class)

class ListSection(p: PaginationSectionInterface, block: SelenideElement? = null) : BaseBlock(
    if (block != null) {
        block.find(By.className("ant-table-content"))
    } else {
        S(By.className("ant-table-content"))
    }
), ListTableInterface, PaginationSectionInterface by p {

    override var list: HumanSelenideElement
        @Step("Инициализация списка созданных объектов")
        get() = content.find(byClassName("ant-table-tbody")).withTitle("Список созданных объектов")
        set(list) {
            this.list = list
        }

    //override var list = content.find(byClassName("ant-table-tbody")).withTitle("Список созданных объектов")

    @Step("Удалить '{0}'")
    override fun delete(cellValue: String, listBlock: SelenideElement) {

        /*val cells = content.findAll(By.tagName("td")).iterator()

        cells.forEach {
            if (it.text.equals(cellValue)) {
                it.hover()
                cells.next()
                cells.next().find(By.tagName("a")).click()
            }
        }*/

        val rows = listBlock.findAll(byClassName("ant-table-row"))

        rows.first { it.text.contains(cellValue) }
            .hover()
            .find(byClassName("data-column-action"))
            .find(By.tagName("a"))
            .click()

        S(byText("Да")).waitUntil(visible, 50000)
        S(byClassName("ant-modal-body")).find(byClassName("ant-btn-primary")).click()
    }

    @Step("Подтвердить удаление группы")
    override fun approve(value: String) {
        waiting(2000)
        S(byText(value)).waitUntil(visible, 50000)
        SS(byClassName("ant-btn")).first { it.text.equals(value) }.click()
        //SS(byClassName("ant-btn")).findBy(text(value)).click()
    }

    @Step("Открыть '{0}' в списке")
    override fun openInList(cellValue: String, content: SelenideElement) {

        val cell = findItemByText(cellValue, content)
        cell?.find(By.cssSelector("a"))?.click()
        waiting(1000)

/*        val cells = list.findAll(By.tagName("td")).iterator()

        cells.forEach {
            if (it.text.equals(cellValue)) {
                it.find(By.cssSelector("a")).click()
            }
        }*/
    }

    @Step("Открыть '{0}' в списке в модальном окне")
    override fun openInModalList(cellValue: String, content: SelenideElement) {

        val cell = findItemInModal(cellValue, content)
        cell?.find(By.cssSelector("a"))?.click()
        waiting(1000)
    }

    @Step("Открыть '{0}' в поле")
    override fun openInField(content: SelenideElement, linkText: String) {
        waiting(1000)
        content.find(byLinkText(linkText)).click()
        waiting(1000)
    }

    @Step("Поиск элемента '{1}' в дереве на странице '{2}'")
    override fun findItemByText(text: String, content: SelenideElement, countTry: Int): ExpandSelenideElement? {
        if (!content.attr("class")!!.contains("ant-modal-content")) {
            //refreshPage()
        }
        content.waitUntil(visible, 5000)
        var td = content.findAll(By.tagName("td")).find {
            it.text == text
        }
        val nextPageButton = content.find(byTitle("Вперёд"))
        while (!nextPageButton.attr("class")!!.contains("ant-pagination-disabled")) {
            if (td == null) {
                nextPageButton.click()
            } else break
            td = content.findAll(By.tagName("td")).find {
                it.text == text
            }

        }
        /*val td = content.findAll(By.tagName("td")).find {
            it.text == text
        }
        if (td == null && nextPageButton.isEnabled && countTry < 10) {
            nextPageButton.click()
            waiting(500)
            return findItemByText(text, content, countTry + 1)
        }
        if ((td == null) && (nextPageButton.isEnabled)) {
            nextPageButton.click()
        }*/
        return td?.let { ExpandSelenideElement(it) }
    }

    @Step("Поиск '{0}' на форме заполнения")
    override fun findItemInModal(elementValue: String, content: SelenideElement): SelenideElement? {

        content.waitUntil(visible, 5000)
        val td = content.findAll(By.tagName("td")).find {
            it.text == elementValue
        }
        return td
    }

    @Step("Поиск {0} по тексту")
    override fun findMessageByText(text: String): SelenideElement {
        return S(byText(text))
    }

    @Step("Поиск элемента {0} в списке")
    override fun findInList(text: String): Boolean {
        scrollToSearchingElement(text)
        val dropDownSelect = SS(byClassName("ant-select-dropdown")).last()
        return dropDownSelect.find(byText(text)).exists()
    }

    override fun toString(): String {
        return "Список объектов"
    }
}