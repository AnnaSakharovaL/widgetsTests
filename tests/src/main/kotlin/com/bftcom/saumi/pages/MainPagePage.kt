package pages

import com.bftcom.utils.*
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selectors.byClassName
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.SelenideElement
import io.qameta.allure.Step

object DashboardMainPage : PageBuilder<MainPagePage>(MainPagePage::class)

class MainPagePage : BaseBlock(S(byClassName("page-content"))), ListTableInterface by ListSection(PaginationSection()) {

    lateinit var workPanelFormOpener: HumanSelenideElement
    lateinit var workPanelSelector: ListHumanElement
    lateinit var widgetSelector: HumanSelenideElement
    lateinit var widgetCard: HumanSelenideElement
    lateinit var filter: HumanSelenideElement

    init {
        initBlock {
            Selenide.sleep(1000)
            workPanelFormOpener = content.find(byClassName("ant-collapse-item")).withTitle("Форма для отображения рабочих панелей")
            workPanelSelector = content.findAll(byClassName("ant-select-selection-search"))[0].withTitleForList("Список рабочих панелей")
            widgetCard = content.find(byClassName("ant-card")).withTitle("Карточка пустого виджета")
            filter = content.findAll(byClassName("ant-radio-button")).first().withTitle("фильтр")
        }
    }

    @Step("Выставление фильтра")
    fun checkFilter() {
        S(byClassName("ant-radio-button-wrapper")).waitUntil(visible, 5000)
        content.findAll(byClassName("ant-radio-button-wrapper")).first { it.text == "«Заветы Ильича»" }.click()
    }

    @Step("Находим все карточки виджетов")
    private fun findAllSearchingElements(elementClass: String): Collection<SelenideElement> {
        content.find(byClassName(elementClass)).waitUntil(visible, 5000)
        return content.findAll(byClassName(elementClass))
    }

    @Step("Проверяем текст содержимого виджета или его заголовка")
    private fun checkWidgetBodyOrTitleText(searchText: String, elementClass: String): Boolean {

        content.find(byClassName(elementClass)).waitUntil(visible, 5000)
        val cards = findAllSearchingElements(elementClass)
        val s = cards
        s.forEach {
            println("------------" + it.text)
        }
        return cards.any {
            it.text.equals(searchText)
        }
    }

    @Step("Проверяем наличие '{0}' в карточке виджета")
    fun isWidgetNameCorrect(searchText: String): Boolean {
        return checkWidgetBodyOrTitleText(searchText, "ant-card-head-title")
    }

    @Step("Проверяем наличие '{0}' на карточке виджета")
    fun isWidgetDisplayed(searchText: String): Boolean {
        return checkWidgetBodyOrTitleText(searchText, "ant-card-body")
    }

    @Step("Получаем количесво карточек виджетов на панели")
    fun getWidgetNumber(): Int {
        return findAllSearchingElements("ant-card").size
    }

    @Step("Проверка значения параметра '{0}}'")
    fun checkBooleanParameter(paramName: String, isGlobal: Boolean): Boolean {
        val params = if (isGlobal)
            findAllSearchingElements("ant-row")
        else
            findAllSearchingElements("ant-card-body")
        val param = params
            .first { it.text.contains(paramName) }
            .find(byClassName("ant-checkbox-input"))
        return param.isSelected
    }

    @Step("Открываем подзадачи в диаграмме Ганта")
    fun openSubtasks() {
        S(byClassName("gantt_task_content")).waitUntil(visible, 5000)
        val subtasksOpeners = content.findAll(byClassName("gantt_open"))
        subtasksOpeners.forEach {
            content.find(byClassName("gantt_open")).click()
        }
    }

    override fun toString(): String {
        return "Главная страница"
    }
}

