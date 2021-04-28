package pages

import com.bftcom.utils.*
import com.codeborne.selenide.Selectors.byClassName
import io.qameta.allure.Step
import org.openqa.selenium.By

object WidgetEditForm : PageBuilder<WidgetEditFormPage>(WidgetEditFormPage::class)

class WidgetEditFormPage : BaseBlock(S(byClassName("widget-page-content"))), ListTableInterface by ListSection(PaginationSection()) {

    val lazyClear = resettableManager()
    val header: HumanSelenideElement by resettableLazy(lazyClear) {
        SS(byClassName("page-header")).first {
            !it.attr("class")!!.contains("ant-layout-header")
        }.withTitle("Действия")
    }
    val makeGroupButton: HumanSelenideElement by resettableLazy(lazyClear) {
        content.find(SwitchButton("Создать группу из этого виджета")).withTitle("Создание группы из этого виджета")
    }
    val additionalButton: HumanSelenideElement by resettableLazy(lazyClear) {
        header.find(byClassName("ant-btn-icon-only")).withTitle("Дополнительно")
    }
    val previewButton: HumanSelenideElement by resettableLazy(lazyClear) {
        S(byClassName("ant-dropdown")).findAll(byClassName("ant-dropdown-menu-item")).last().withTitle("Предпросмотр")
    }
    val nameField: TextByUserHumanElement by resettableLazy(lazyClear) {
        content.find(TextField("Название")).withTitleForTextUser("Название")
    }
    val typeField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Тип")).withTitleForList("Тип")
    }
    val dataSourceField: ObjectHumanElement by resettableLazy(lazyClear) {
        content.find(ObjectField("Источник данных")).withTitleForObject("Источник данных")
    }
    val dataSourceFieldOpener: HumanSelenideElement by resettableLazy(lazyClear) {
        content.find(By.className("ant-input-group-addon")).withTitle("Открывашка для поля Источник данных")
    }
    val addTableButton: HumanSelenideElement by resettableLazy(lazyClear) {
        content.find(Button("Добавить")).withTitle("Добавить")
    }
    val diagramTypeField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Тип диаграммы")).withTitleForList("Тип диаграммы")
    }
    val axisXField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Значение по оси Х")).withTitleForList("Значение по оси X")
    }
    val axisYField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(MultiValuedListField("Значения по оси Y")).withTitleForList("Значение по оси Y")
    }
    val valueField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Значение")).withTitleForList("Значение")
    }
    val taskField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Задача")).withTitleForList("Задача")
    }
    val eventField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Мероприятие")).withTitleForList("Мероприятие")
    }
    val cardWithEventField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Карточка объекта при выборе мероприятия")).withTitleForList("Карточка объекта при выборе мероприятия")
    }
    val eventStartDateField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Начало мероприятия")).withTitleForList("Начало мероприятия")
    }
    val eventEndDateField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Конец мероприятия")).withTitleForList("Конец мероприятия")
    }
    val planType: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Тип плана")).withTitleForList("Тип плана")
    }
    val fieldTypeField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Поле, из которого берется значение")).withTitleForList("Поле, из которого берется значение")
    }
    val displayNameField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Поле с отображаемым именем")).withTitleForList("Поле с отображаемым именем")
    }
    val timeLabelField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Поле с меткой времени")).withTitleForList("Поле с меткой времени")
    }
    val valueSourceTypeField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Тип источника значения")).withTitleForList("Тип источника значения")
    }
    val mapTypeField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Тип карты")).withTitleForList("Тип карты")
    }
    val regionCodeField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Поле с кодом области")).withTitleForList("Поле с кодом области")
    }
    val valueForColorCalculationField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Поле с значением для вычисления цвета")).withTitleForList("Поле с значением для вычисления цвета")
    }
    val getMinValueFromSourceField: BooleanHumanElement by resettableLazy(lazyClear) {
        content.find(BooleanField("Брать минимальное значение из источника")).withTitleForBoolean("Брать минимальное значение из источника")
    }
    val getMaxValueFromSourceField: BooleanHumanElement by resettableLazy(lazyClear) {
        content.find(BooleanField("Брать максимальное значение из источника")).withTitleForBoolean("Брать максимальное значение из источника")
    }
    val minColorValueField: TextHumanElement by resettableLazy(lazyClear) {
        content.find(NumberField("Минимальное значение")).withTitleForText("Минимальное значение")
    }
    val maxColorValueField: TextHumanElement by resettableLazy(lazyClear) {
        content.find(NumberField("Максимальное значение")).withTitleForText("Максимальное значение")
    }
    val intervalCountField: TextHumanElement by resettableLazy(lazyClear) {
        content.find(NumberField("Количество интервалов для раскраски")).withTitleForText("Количество интервалов для раскраски")
    }
    val addColumnButton: HumanSelenideElement by resettableLazy(lazyClear) {
        content.find(ButtonInFieldGroup("Добавить в Список колонок")).withTitle("Добавить в список колонок")
    }
    val addButton: HumanSelenideElement by resettableLazy(lazyClear) {
        content.find(Button("Добавить")).withTitle("Добавить")
    }
    val saveButton: HumanSelenideElement by resettableLazy(lazyClear) {
        header.find(Button("Сохранить")).withTitle("Сохранить")
    }
    val cancelButton: HumanSelenideElement by resettableLazy(lazyClear) {
        header.find(Button("Отменить")).withTitle("Отменить")
    }

    @Step("Очищаем свойства при вызове объекта для новой формы")
    fun clearProperties() {
        lazyClear.reset()
    }

    override fun toString(): String {
        return "Форма заполнения данных виджета"
    }
}

