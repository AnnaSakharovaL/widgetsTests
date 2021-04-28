package pages

import com.bftcom.utils.*
import com.codeborne.selenide.Selectors.*
import com.codeborne.selenide.SelenideElement
import io.qameta.allure.Step

interface ActionsInWidgetsMenuInterface : ListTableInterface, PaginationSectionInterface {

    var addToBlockButton: HumanSelenideElement
    var blockOpener: HumanSelenideElement
    var searchField: TextByUserHumanElement
    var searchParamField: TextHumanElement
    var actionsInWidgetAdminLazyClear: ResettableLazyManager
    var listBlock: SelenideElement

    fun findItemInWidgetListByText(text: String, content: SelenideElement): ExpandSelenideElement?
    fun openBlock()
    fun closeBlock()
    fun clearProperties()
}

object ActionsInWidgetsMenu : PageBuilder<ActionsInWidgetsMenuBlockSection>(ActionsInWidgetsMenuBlockSection::class)

class ActionsInWidgetsMenuBlockSection(blockName: String) : BaseBlock(getContentElement(blockName)), ActionsInWidgetsMenuInterface,
    ListTableInterface by ListSection(PaginationSection(getContentElement(blockName)), getContentElement(blockName)),
    PaginationSectionInterface by PaginationSection(getContentElement(blockName)) {

    override var actionsInWidgetAdminLazyClear = resettableManager()
    val block by resettableLazy(actionsInWidgetAdminLazyClear) {
        SS(byClassName("field-group")).first { it.text.contains(blockName) }
    }
    val addButton by resettableLazy(actionsInWidgetAdminLazyClear) {
        block.find(byTitle("Добавить новую запись")).withTitle("Добавить новую запись")
    }
    val opener by resettableLazy(actionsInWidgetAdminLazyClear) {
        block.find(byTagName("a")).withTitle("Открытие блока")
    }
    val search by resettableLazy(actionsInWidgetAdminLazyClear) {
        block.find(byAttribute("placeholder", "Название")).withTitleForTextUser("Поиск")
    }
    val searchParam by resettableLazy(actionsInWidgetAdminLazyClear) {
        block.find(byAttribute("placeholder", "Отображаемое имя")).withTitleForText("Поиск")
    }

    override var listBlock: SelenideElement
        @Step("Инициализация блка в меню")
        get() = block
        set(listBlock) {
            this.listBlock = listBlock
        }

    override var addToBlockButton: HumanSelenideElement
        @Step("Инициализация кнопки добавления новой записи")
        get() = addButton
        set(addToBlockButton) {
            this.addToBlockButton = addToBlockButton
        }

    override var blockOpener: HumanSelenideElement
        @Step("Инициализация ссылки для открытия блока")
        get() = opener
        set(blockOpener) {
            this.blockOpener = blockOpener
        }

    override var searchField: TextByUserHumanElement
        @Step("Инициализация поля поиска")
        get() = search
        set(searchField) {
            this.searchField = searchField
        }

    override var searchParamField: TextHumanElement
        @Step("Инициализация поля поиска")
        get() = searchParam
        set(searchParamField) {
            this.searchParamField = searchParamField
        }

    override fun findItemInWidgetListByText(text: String, content: SelenideElement): ExpandSelenideElement? = findItemByText(text, content)

    override fun openBlock() {
        if (block.attr("class")!!.contains("field-group-collapsed")) {
            blockOpener.click()
        }
    }

    override fun closeBlock() {
        if (!(block.attr("class")!!.contains("field-group-collapsed"))) {
            blockOpener.click()
        }
    }

    override fun clearProperties() {
        actionsInWidgetAdminLazyClear.reset()
    }

    override fun toString(): String {
        return "Блок действий"
    }
}

fun getContentElement(blockName: String): SelenideElement {
    val contentElement = SS(byClassName("field-group")).first { it.text.contains(blockName) }
    return contentElement
}
