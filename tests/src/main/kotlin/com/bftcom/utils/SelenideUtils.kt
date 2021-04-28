package com.bftcom.utils

import com.codeborne.selenide.*
import com.codeborne.selenide.Condition.enabled
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selectors.*
import com.codeborne.selenide.Selenide.refresh
import io.qameta.allure.Step
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement

fun SS(seleniumSelector: By): ElementsCollection {
    return Selenide.`$$`(seleniumSelector)
}

fun S(seleniumSelector: By): SelenideElement {
    return Selenide.`$`(seleniumSelector)
}

fun S(cssSelector: String): SelenideElement {
    return Selenide.`$`(cssSelector)
}

fun S(element: SelenideElement): SelenideElement {
    return Selenide.`$`(element)
}

fun S(webElement: WebElement): SelenideElement {
    return Selenide.`$`(webElement)
}

@Step("Обновили страницу")
fun refreshPage() {
    refresh()
}

@Step("Ждем подгрузку")
fun waiting(mlsec: Long) {
    Selenide.sleep(mlsec)
}

@Step("Открыли '{0.human}'")
fun SelenideElement.openInList() = click()

@Step("Нажали '{0.human}'")
fun SelenideElement.makeClick() {
    click()
    waiting(500)
}

@Step("Нажали на '{0.human}'")
fun SelenideElement.push() = click()

@Step("Вызвали '{0.human}'")
fun SelenideElement.call() = click()

@Step("В поле '{0.human}' ввели значение '{1}'")
fun SelenideElement.textFilling(value: String): SelenideElement {
    this.sendKeys(Keys.CONTROL, Keys.chord("A"))
    this.sendKeys(Keys.DELETE)
    this.setValue(value)
    return this
}

@Step("В поле '{0.human}' ввели значение '{1}'")
fun SelenideElement.textFillingLikeUser(value: String): SelenideElement {

    //this.sendKeys(Keys.CONTROL, Keys.chord("A"))
    //this.sendKeys(Keys.DELETE)

    val currentValue = this.value
    currentValue?.forEach {
        this.sendKeys(Keys.BACK_SPACE)
    }

    value.forEach {
        this.`val`(it.toString())
    }
    return this
}

/*@Step("В поле '{0.human}' ввели значение '{1}'")
fun SelenideElement.textFillingLikeUser(value: String): SelenideElement {

    val currentValue = this.value
    currentValue?.forEach {
        this.sendKeys(Keys.BACK_SPACE)
    }

    value.forEach {
        this.`val`(it.toString())
    }
    return this
}*/

@Step("В поле списка '{0.human}' выбрали значение '{1}'")
fun SelenideElement.listFilling(value: String): SelenideElement {
    this.waitUntil(enabled, 5000)
    this.click()
    scrollListUp()
    val items = scrollToSearchingElement(value)
    items.first { it.text.equals(value) }.click()
    return this
}

@Step("Поиск в списке")
fun scrollToSearchingElement(value: String): ElementsCollection {
    val dropDownSelect = SS(byClassName("ant-select-dropdown")).last()
    var items = dropDownSelect.findAll(byClassName("ant-select-item"))
    var lastItem: String
    do {
        if (!dropDownSelect.find(byText(value)).exists()) {
            lastItem = items.last().text
            items.last().scrollIntoView(true)
            items = dropDownSelect.findAll(byClassName("ant-select-item"))
        } else {
            break
        }
    } while (!lastItem.equals(items.last().text))
    return items
}

fun scrollListUp() {
    val dropDownSelect = SS(byClassName("ant-select-dropdown")).last()
    var items = dropDownSelect.findAll(byClassName("ant-select-item"))
    var firstItem: String
    do {
        firstItem = items.first().text
        items.first().scrollIntoView(false)
        items = dropDownSelect.findAll(byClassName("ant-select-item"))
    } while (!firstItem.equals(items.first().text))
}

@Step("В поле списка объектов '{0.human}' выбрали значение '{1}'")
fun SelenideElement.objectsListFilling(value: String): SelenideElement {
    /*this.click()
    Thread.sleep(1000)
    val selectTreeList = SS(byClassName("ant-select-tree-list")).last()
    val items = selectTreeList.findAll(byClassName("ant-select-tree-treenode"))
    items.first {it.text.contains(objectsList[0])} . find(byClassName("ant-select-tree-switcher")) . click()
    items.first {it.text.contains(objectsList[1])} . click()*/
    this.textFilling(value)
    Thread.sleep(1000)
    val selectTreeList = SS(byClassName("ant-select-tree-list")).last()
    val items = selectTreeList.findAll(byClassName("ant-select-tree-treenode"))
    items.first { it.text.contains(value) }.click()
    return this
}

@Step("В поле '{0.human}' задали значение '{1}'")
fun SelenideElement.objectFilling(value: String): SelenideElement {
    this.textFilling(value)
    Thread.sleep(1000)
    S(byValue(value)).waitUntil(Condition.appears, 5000)
    Thread.sleep(500)
    this.sendKeys(Keys.ENTER)
    Thread.sleep(1000)
    return this
}

@Step("В поле '{0.human}' задали значение '{1}'")
fun SelenideElement.flagFilling(value: Boolean) {
    if (value) {
        if (!this.isSelected) {
            this.click()
        } else {
            if (this.isSelected) {
                this.click()
            }
        }
    }
}

@Step("В поле '{0.human}' содержится текст '{1}'")
fun SelenideElement.containText(value: String) = shouldHave(Condition.text(value))

@Step("Находим кнопку '{1}' по названию")
fun SelenideElement.findButton(buttonName: String): SelenideElement {
    this.waitUntil(visible, 5000)
    val collection = this.findAll(byClassName("ant-btn"))
    val button = collection.first { it.text.equals(buttonName) }
    return button
}

@Step("Находим кнопку '{1}' в группе полей по названию")
fun SelenideElement.findButtonInFieldGroup(buttonAndGroupName: String): SelenideElement {
    val parts = buttonAndGroupName.split(" в ")
    val buttonName = parts[0]
    val fieldGroupName = parts[1]
    val fieldGroups = this.findAll(byClassName("field-group"))
    return fieldGroups.first { it.text.contains(fieldGroupName) }.find(byTitle(buttonName))
}

@Step("Находим переключатель '{1}' по названию")
fun SelenideElement.findSwitchButton(switchName: String): SelenideElement {
    this.waitUntil(visible, 5000)
    val switchRow = this.find(byClassName("widget-admin-panel-item"))
    return switchRow.find(byClassName("ant-switch"))
}

@Step("Находим текстовое поле '{1}' по названию")
fun SelenideElement.findTextField(fieldName: String): SelenideElement {
    return field(fieldName, "ant-input")
}

@Step("Находим поле для ввода чисел '{1}' по названию")
fun SelenideElement.findNumberField(fieldName: String): SelenideElement {
    return field(fieldName, "ant-input-number-input")
}

@Step("Находим поле списка '{1}' по лейблу")
fun SelenideElement.findListField(fieldName: String): SelenideElement {
    return field(fieldName, "ant-select-show-arrow")
}

@Step("Находим поле объекта '{1}' по лейблу")
fun SelenideElement.findObjectField(fieldName: String): SelenideElement {
    return field(fieldName, "ant-input")
}

@Step("Находим поле мультиселекта '{1}' по лейблу")
fun SelenideElement.findMultivaluedListField(fieldName: String): SelenideElement {
    return field(fieldName, "ant-select")
}

@Step("Находим поле мультиселекта '{1}' по лейблу")
fun SelenideElement.findMultivaluedField(fieldName: String): SelenideElement {
    return field(fieldName, "ant-select-selection-search-input")
}

@Step("Находим поле флага '{1}' по лейблу")
fun SelenideElement.findFlagField(fieldName: String): SelenideElement {
    return field(fieldName, "ant-checkbox-input")
}

@Step("Находим многострочное поле '{1}' по лейблу")
fun SelenideElement.findTextAreaField(fieldName: String): SelenideElement {
    return field(fieldName, "textarea")
}

fun SelenideElement.field(labelText: String, className: String): SelenideElement {
    this.waitUntil(visible, 5000)
    lateinit var field: SelenideElement
    lateinit var tabs: Collection<SelenideElement>
    if (this.find(byClassName("ant-tabs")).exists()) {
        tabs = this.findAll(byClassName("ant-tabs"))
    } else if (this.find(byClassName("ant-tabs")).exists()) {
        tabs = this.findAll(byClassName("ant-row"))
    } else {
        tabs = SS(byClassName("widget-page-content"))
    }

    //if (antTabs.first().find(byClassName("ant-row")).exists()) {
    //   val rows = antTabs.first().findAll(byClassName("ant-row"))
    //   val divs = rows.first { it.text.equals(labelText)} . findAll(By.tagName("div")).iterator()
    //    divs.forEach {if (it.text.equals(labelText)) { field = divs.next().find(byClassName(className)) } }
    //} else {
    val labels = tabs.first { it.text.contains(labelText) }.findAll(By.tagName("div")).iterator()
    labels.forEach {
        if (it.text.equals(labelText) && (it.getAttribute("class")!!.contains("ant-form-item-label"))) {
            field = labels.next().find(byClassName(className))
        }
    }
    //}
    return field
}

fun SelenideElement.find(label: ElementType): SelenideElement {
    val element: SelenideElement
    when (label) {
        is TextField -> element = findTextField(label.text)
        is NumberField -> element = findNumberField(label.text)
        is ListField -> element = findListField(label.text)
        is ObjectListSeleniumField -> element = findMultivaluedField(label.text)
        is ObjectField -> element = findObjectField(label.text)
        is MultiValuedListField -> element = findMultivaluedListField(label.text)
        is MultiValuedObjectField -> element = findMultivaluedField(label.text)
        is BooleanField -> element = findFlagField(label.text)
        is Button -> element = findButton(label.text)
        is ButtonInFieldGroup -> element = findButtonInFieldGroup(label.text)
        is TextAreaField -> element = findTextAreaField(label.text)
        is SwitchButton -> element = findSwitchButton(label.text)
    }
    return element
}

@Step("В поле '{0.human}' проставили значение '{1}'")
operator fun HumanSelenideElement.invoke(value: String) {
    filling(value)
}

@Step("В поле '{0.human}' проставили значение '{1}'")
operator fun HumanSelenideElement.invoke(strFun: () -> String) {
    filling(strFun())
}

@Step("В поле '{0.human}' проставили значение '{1}'")
operator fun HumanSelenideElement.timesAssign(value: String) {
    filling(value)
}

@Step("В поле '{0.human}' проставили значение '{1}'")
infix fun HumanSelenideElement.put(value: String) {
    filling(value)
}

@Step("В поле '{0.human}' проставили значение '{1}'")
infix fun HumanSelenideElement.put(s: Boolean) {
    flagFilling(s)
}

fun HumanSelenideElement.filling(s: String) {
    when (this) {
        is TextHumanElement -> textFilling(s)
        is TextByUserHumanElement -> textFillingLikeUser(s)
        is ListHumanElement -> listFilling(s)
        is ObjectHumanElement -> objectFilling(s)
        is ObjectListHumanElement -> objectsListFilling(s)
        is BaseHumanElement -> {
            try {
                listFilling(s)
            } catch (e: Throwable) {
                try {
                    objectFilling(s)
                } catch (e: Throwable) {
                    textFilling(s)
                }
            }
        }
    }
}