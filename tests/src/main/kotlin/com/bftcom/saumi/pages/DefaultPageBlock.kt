package com.bftcom.saumi.pages

import com.bftcom.utils.*
import com.codeborne.selenide.*
import com.codeborne.selenide.Selectors.*
import io.qameta.allure.Step
import org.openqa.selenium.By

open class DefaultPageBlock : BaseBlock(By.className("page-content")) {

    lateinit var pageHeader: HumanSelenideElement
    lateinit var pageHeaderMain: HumanSelenideElement
    lateinit var pageHeaderActions: HumanSelenideElement

    init {
        initBlock {
            pageHeader = content.find(By.className("page-header")).withTitle("Шапка страницы контента")
            pageHeaderMain = pageHeader.find(By.className("page-header-main")).withTitle("Заголовок шапки страницы контента")
            pageHeaderActions = pageHeader.find(By.className("page-header-actions")).withTitle("Блок действий шапки страницы контента")
        }
    }

    override fun toString(): String {
        return "Шаблон под страницу"
    }
}

open class DefaultEditBlock : BaseBlock(By.className("ant-modal-content")) {

    lateinit var headerForm: HumanSelenideElement
    lateinit var bodyForm: HumanSelenideElement
    lateinit var footerForm: HumanSelenideElement

    init {
        initBlock {
            headerForm = content.find(By.className("ant-modal-header")).withTitle("Блок заголовка формы")
            bodyForm = content.find(By.className("ant-modal-body")).withTitle("Блок контента формы")
            footerForm = content.find(By.className("ant-modal-footer")).withTitle("Блок футера формы")
        }
    }

    @Step("Заполнить поле '{0}' значением '{1}'")
    fun fillFieldInGridForm(name: String, value: String) {
        val labels = bodyForm.findElements(byCssSelector("div.ant-form-item-label label"))
        val controls = bodyForm.findAll(byCssSelector("div.ant-form-item-control-wrapper"))

        labels.indexOfFirst { it.text == name }.takeIf { it > -1 }?.let {
            fillFieldByField(controls[it], value)
        }
    }

    @Step("Заполнить поле '{0}' значением '{1}'")
    fun fillFieldInRowForm(name: String, value: String) {
        val formRows = bodyForm.findAll(By.className("ant-row"))
        val field = formRows.firstOrNull { it.text.contains(name) } ?: throw IllegalStateException("поле не найдено $name")
        fillFieldByField(field, value)
    }

    //TODO добавить умный поиск поля для использования на форме
    @Step("Заполнить поле '{0}' значением '{1}'")
    fun fillField(name: String, value: String) {

    }

    fun fillFieldByField(field: SelenideElement, value: String) {
        when {
            field.find("div.ant-form-item-control.multiple-select-field").exists() -> {
                field.findElement(By.className("ant-select-selection__rendered")).click()

                Selenide.sleep(1000)
                val s = SS(By.className("ant-select-dropdown-menu-root"))
                s.find { it.innerText().contains(value) }?.find(byText(value))?.click()
            }
            field.find("div.ant-form-item-control.select-field").exists() -> {
                field.findElement(By.className("ant-select-selection__rendered")).click()
                S(".ant-select-dropdown-menu-root").find(byText(value)).click()
            }
            field.find(".ant-input-group-addon").exists() -> {
                field.find("div.ant-form-item-control.lookup-field").click()

                //val modalTitle = "Выберите значение поля"//\"$fieldName\""
                val modalTitle = "Выберите значение поля \"Вышестоящее подразделение\""

                //SS(By.className("ant-modal-content")).find { it.find(By.className("ant-modal-header")).find() }
                //println(">>>>>>>>>>>>> ${S(byText(modalTitle))}")
                S(byText(modalTitle)).waitUntil(Condition.visible, 50000)

                val modal = S(By.tagName("body")).findAll(By.className("ant-modal-content")).find {
                    it.find(By.className("ant-modal-title")).text.equals(modalTitle)
                }
                val paginationNext = modal?.find(byTitle("Вперёд"))
                do {
                    val field = modal?.findAll(By.cssSelector(".ant-table-row td"))?.find {
                        it.find(byText(value)).exists()
                    }
                    if (field !== null) {
                        field.click()
                    } else {
                        paginationNext?.click()
                        Selenide.sleep(1000)
                    }
                } while (field == null)

                S(byText(modalTitle)).shouldBe(Condition.disappear)
            }
            field.find(By.tagName("input")).exists() -> {
                field.find(By.tagName("input")).`val`(value)
                //field?.find(By.tagName("input"))?.`val`(value)
                //field?.find(By.tagName("input"))?.sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), value)
            }
        }
    }

    @Step("Вызвать действие по сохранению формы")
    fun save() {
        footerForm.find(By.className("ant-btn-primary")).click()
    }

    override fun toString(): String {
        return "Форма редактирования по умолчанию"
    }
}