package com.bftcom.saumi.widgets.create

import com.bftcom.utils.*
import com.codeborne.selenide.Condition.visible
import io.qameta.allure.*
import org.junit.Assert.assertNotNull
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import pages.*

@Execution(ExecutionMode.CONCURRENT)
@Epic("Виджеты")
class CreateWidgetsTests : ForTests() {

    val userLogin = "root4"
    val userPassword = "root"

    @BeforeEach
    fun beforeEach() {
        login(userLogin, userPassword)
    }

    @Feature("Создание виджетов")
    @Story("Успешное создание виджета типа Таблица со списком объектов")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun tableWithObjectsListWidgetCreationTest() {

        val widgetName = "Тестовый виджет".withRandomPostfix()

        MainMenu {
            nowIs(visible)
            go("Администрирование", "Виджеты")
        }
        WidgetsList {
            openBlock()
            addToBlockButton.makeClick()
        }
        WidgetEditForm {
            nameField put widgetName
            typeField put "Таблица со списком объектов"
            addTableButton.makeClick()
        }
        TableSelectionForm {
            appObjectField put "Корова"
            saveButton.makeClick()
        }
        WidgetEditForm {
            saveButton.makeClick()
            checkIn("сообщение об успешном создании виджета отображается") {
                assertNotNull(findMessageByText("Запись сохранена"))
            }
            approve("Да")
            checkIn("сообщение об успешном создании группы виджетов отображается") {
                assertNotNull(findMessageByText("Группа успешно создана"))
            }
        }
        WidgetsList {
            searchField put widgetName
            checkIn("'$widgetName' появился в списке виджетов") {
                assertNotNull(findItemInWidgetListByText(widgetName, content))
            }
        }
    }

    @Feature("Создание виджетов")
    @Story("Успешное создание виджета типа Карточка объекта")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun objectCardWidgetCreationTest() {

        val widgetName = "Тестовый виджет".withRandomPostfix()

        MainMenu {
            nowIs(visible)
            go("Администрирование", "Виджеты")
        }
        WidgetsList {
            openBlock()
            addToBlockButton.makeClick()
        }
        WidgetEditForm {
            nameField put widgetName
            typeField put "Карточка объекта"
            dataSourceField put "Договор аренды"
            saveButton.makeClick()
            checkIn("сообщение об успешном создании виджета отображается") {
                assertNotNull(findMessageByText("Запись сохранена"))
            }
            approve("Да")
            checkIn("сообщение об успешном создании группы виджетов отображается") {
                assertNotNull(findMessageByText("Группа успешно создана"))
            }
        }
        WidgetsList {
            searchField put widgetName
            checkIn("'$widgetName' появился в списке виджетов") {
                assertNotNull(findItemInWidgetListByText(widgetName, content))
            }
        }
    }

    @Feature("Создание виджетов")
    @Story("Успешное создание виджета типа График или диаграмма")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun diagramWidgetCreationTest() {

        val widgetName = "Тестовый виджет".withRandomPostfix()

        MainMenu {
            go("Администрирование", "Виджеты")
        }
        WidgetsList {
            openBlock()
            addToBlockButton.makeClick()
        }
        WidgetEditForm {
            nameField put widgetName
            typeField put "График или диаграмма"
            dataSourceField put "Удой коровы по дням"
            diagramTypeField put "Столбчатая диаграмма"
            axisXField put "Дата"
            axisYField put "Корова"
            saveButton.makeClick()
            checkIn("сообщение об успешном создании виджета отображается") {
                assertNotNull(findMessageByText("Запись сохранена"))
            }
            approve("Да")
            checkIn("сообщение об успешном создании группы виджетов отображается") {
                assertNotNull(findMessageByText("Группа успешно создана"))
            }
        }
        WidgetsList {
            searchField put widgetName
            checkIn("'$widgetName' появился в списке виджетов") {
                assertNotNull(findItemInWidgetListByText(widgetName, content))
            }
        }
    }

    @Feature("Создание виджетов")
    @Story("Успешное создание виджета типа Географическая карта")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun mapWidgetCreationTest() {

        val widgetName = "Тестовый виджет".withRandomPostfix()

        MainMenu {
            go("Администрирование", "Виджеты")
        }
        WidgetsList {
            openBlock()
            addToBlockButton.makeClick()
        }
        WidgetEditForm {
            nameField put widgetName
            typeField put "Географическая карта"
            dataSourceField put "Корова"
            saveButton.makeClick()
            checkIn("сообщение об успешном создании виджета отображается") {
                assertNotNull(findMessageByText("Запись сохранена"))
            }
            approve("Да")
            checkIn("сообщение об успешном создании группы виджетов отображается") {
                assertNotNull(findMessageByText("Группа успешно создана"))
            }
        }
        WidgetsList {
            searchField put widgetName
            checkIn("'$widgetName' появился в списке виджетов") {
                assertNotNull(findItemInWidgetListByText(widgetName, content))
            }
        }
    }

    @Feature("Создание виджетов")
    @Story("Успешное создание виджета типа Список объектов")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun objectsListWidgetCreationTest() {

        val widgetName = "Тестовый виджет".withRandomPostfix()

        MainMenu {
            nowIs(visible)
            go("Администрирование", "Виджеты")
        }
        WidgetsList {
            openBlock()
            addToBlockButton.makeClick()
        }
        WidgetEditForm {
            nameField put widgetName
            typeField put "Список объектов"
            dataSourceField put "Список коров"
            addColumnButton.makeClick()
        }
        FieldSelectionFormForObjectsListWidget {
            lazyClear.reset()
            fieldNameField put "Корова"
            saveButton.makeClick()
        }
        WidgetEditForm {
            saveButton.makeClick()
            checkIn("сообщение об успешном создании виджета отображается") {
                assertNotNull(findMessageByText("Запись сохранена"))
            }
            approve("Да")
            checkIn("сообщение об успешном создании группы виджетов отображается") {
                assertNotNull(findMessageByText("Группа успешно создана"))
            }
        }
        WidgetsList {
            searchField put widgetName
            checkIn("'$widgetName' появился в списке виджетов") {
                assertNotNull(findItemInWidgetListByText(widgetName, content))
            }
        }
    }

    @Feature("Создание виджетов")
    @Story("Успешное создание виджета типа Чек-лист")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun checkListWidgetCreationTest() {

        val widgetName = "Тестовый виджет".withRandomPostfix()

        MainMenu {
            nowIs(visible)
            go("Администрирование", "Виджеты")
        }
        WidgetsList {
            openBlock()
            addToBlockButton.makeClick()
        }
        WidgetEditForm {
            nameField put widgetName
            typeField put "Список объектов"
            dataSourceField put "Обслуживание машин"
            addColumnButton.makeClick()
        }
        FieldSelectionFormForObjectsListWidget {
            lazyClear.reset()
            fieldNameField put "Название"
            saveButton.makeClick()
        }
        WidgetEditForm {
            saveButton.makeClick()
            checkIn("сообщение об успешном создании виджета отображается") {
                assertNotNull(findMessageByText("Запись сохранена"))
            }
            approve("Да")
            checkIn("сообщение об успешном создании группы виджетов отображается") {
                assertNotNull(findMessageByText("Группа успешно создана"))
            }
        }
        WidgetsList {
            searchField put widgetName
            checkIn("'$widgetName' появился в списке виджетов") {
                assertNotNull(findItemInWidgetListByText(widgetName, content))
            }
        }
    }

    @Feature("Создание виджетов")
    @Story("Успешное создание виджета типа Дорожная карта")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun roadMapWidgetCreationTest() {

        val widgetName = "Тестовый виджет".withRandomPostfix()

        MainMenu {
            go("Администрирование", "Виджеты")
        }
        WidgetsList {
            openBlock()
            addToBlockButton.makeClick()
        }
        WidgetEditForm {
            nameField put widgetName
            typeField put "Дорожная карта"
            dataSourceField put "gantt"
            taskField put "Мероприятие"
            eventField put "Отслеживание"
            cardWithEventField put "Доярка"
            eventStartDateField put "Дата с"
            eventEndDateField put "Дата по"
            saveButton.makeClick()
            checkIn("сообщение об успешном создании виджета отображается") {
                assertNotNull(findMessageByText("Запись сохранена"))
            }
            approve("Да")
            checkIn("сообщение об успешном создании группы виджетов отображается") {
                assertNotNull(findMessageByText("Группа успешно создана"))
            }
        }
        WidgetsList {
            searchField put widgetName
            checkIn("'$widgetName' появился в списке виджетов") {
                assertNotNull(findItemInWidgetListByText(widgetName, content))
            }
        }
    }

    @Feature("Создание виджетов")
    @Story("Успешное создание виджета типа Диаграмма Ганта")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun gantDiagramWidgetCreationTest() {

        val widgetName = "Тестовый виджет".withRandomPostfix()

        MainMenu {
            go("Администрирование", "Виджеты")
        }
        WidgetsList {
            openBlock()
            addToBlockButton.makeClick()
        }
        WidgetEditForm {
            nameField put widgetName
            typeField put "Диаграмма Ганта"
            dataSourceField put "gantt"
            taskField put "Мероприятие"
            saveButton.makeClick()
            checkIn("сообщение об успешном создании виджета отображается") {
                assertNotNull(findMessageByText("Запись сохранена"))
            }
            approve("Да")
            checkIn("сообщение об успешном создании группы виджетов отображается") {
                assertNotNull(findMessageByText("Группа успешно создана"))
            }
        }
        WidgetsList {
            searchField put widgetName
            checkIn("'$widgetName' появился в списке виджетов") {
                assertNotNull(findItemInWidgetListByText(widgetName, content))
            }
        }
    }

    @Feature("Создание виджетов")
    @Story("Успешное создание виджета типа Поэтажный план")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun planWidgetCreationTest() {

        val widgetName = "Тестовый виджет".withRandomPostfix()

        MainMenu {
            go("Администрирование", "Виджеты")
        }
        WidgetsList {
            openBlock()
            addToBlockButton.makeClick()
        }
        WidgetEditForm {
            nameField put widgetName
            typeField put "Поэтажный план"
            dataSourceField put "Колхоз"
            nameField.makeClick()    //тобы скрыть предупреждалку об изменении параметров
            planType put "Объект на планах"
            saveButton.makeClick()
            checkIn("сообщение об успешном создании виджета отображается") {
                assertNotNull(findMessageByText("Запись сохранена"))
            }
            approve("Да")
            checkIn("сообщение об успешном создании группы виджетов отображается") {
                assertNotNull(findMessageByText("Группа успешно создана"))
            }
        }
        WidgetsList {
            searchField put widgetName
            checkIn("'$widgetName' появился в списке виджетов") {
                assertNotNull(findItemInWidgetListByText(widgetName, content))
            }
        }
    }

    @Feature("Создание виджетов")
    @Story("Успешное создание виджета типа Диаграмма спидометр")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun spidometrWidgetCreationTest() {

        val widgetName = "Тестовый виджет".withRandomPostfix()

        MainMenu {
            go("Администрирование", "Виджеты")
        }
        WidgetsList {
            openBlock()
            addToBlockButton.makeClick()
        }
        WidgetEditForm {
            nameField put widgetName
            typeField put "Диаграмма спидометр"
            dataSourceField put "Удой по дояркам"
            nameField.makeClick()
            fieldTypeField put "Доярка"
            addButton.makeClick()
        }
        SpeedometrSectorForm {
            fromField put "1"
            Thread.sleep(1000)
            toField put "100"
            saveButton.makeClick()
        }
        WidgetEditForm {
            saveButton.makeClick()
            checkIn("сообщение об успешном создании виджета отображается") {
                assertNotNull(findMessageByText("Запись сохранена"))
            }
            approve("Да")
            checkIn("сообщение об успешном создании группы виджетов отображается") {
                assertNotNull(findMessageByText("Группа успешно создана"))
            }
        }
        WidgetsList {
            searchField put widgetName
            checkIn("'$widgetName' появился в списке виджетов") {
                assertNotNull(findItemInWidgetListByText(widgetName, content))
            }
        }
    }

    @Feature("Создание виджетов")
    @Story("Успешное создание виджета типа Часы")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun clockWidgetCreationTest() {

        val widgetName = "Тестовый виджет".withRandomPostfix()

        MainMenu {
            go("Администрирование", "Виджеты")
        }
        WidgetsList {
            openBlock()
            addToBlockButton.makeClick()
        }
        WidgetEditForm {
            nameField put widgetName
            typeField put "Часы"
            saveButton.makeClick()
            checkIn("сообщение об успешном создании виджета отображается") {
                assertNotNull(findMessageByText("Запись сохранена"))
            }
            approve("Да")
            checkIn("сообщение об успешном создании группы виджетов отображается") {
                assertNotNull(findMessageByText("Группа успешно создана"))
            }
        }
        WidgetsList {
            searchField put widgetName
            checkIn("'$widgetName' появился в списке виджетов") {
                assertNotNull(findItemInWidgetListByText(widgetName, content))
            }
        }
    }

    @Feature("Создание виджетов")
    @Story("Успешное создание виджета типа Клендарь")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun calendarWidgetCreationTest() {

        val widgetName = "Тестовый виджет".withRandomPostfix()

        MainMenu {
            nowIs(visible)
            go("Администрирование", "Виджеты")
        }
        WidgetsList {
            openBlock()
            addToBlockButton.makeClick()
        }
        WidgetEditForm {
            nameField put widgetName
            typeField put "Календарь"
            dataSourceField put "Удой по дояркам"
            displayNameField put "Доярка"
            timeLabelField put "Дата"
            saveButton.makeClick()
            checkIn("сообщение об успешном создании виджета отображается") {
                assertNotNull(findMessageByText("Запись сохранена"))
            }
            approve("Да")
            checkIn("сообщение об успешном создании группы виджетов отображается") {
                assertNotNull(findMessageByText("Группа успешно создана"))
            }
        }
        WidgetsList {
            searchField put widgetName
            checkIn("'$widgetName' появился в списке виджетов") {
                assertNotNull(findItemInWidgetListByText(widgetName, content))
            }
        }
    }

    @Feature("Создание виджетов")
    @Story("Успешное создание виджета типа Индикатор")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun indicatorWidgetCreationTest() {

        val widgetName = "Тестовый виджет".withRandomPostfix()

        MainMenu {
            nowIs(visible)
            go("Администрирование", "Виджеты")
        }
        WidgetsList {
            openBlock()
            addToBlockButton.makeClick()
        }
        WidgetEditForm {
            nameField put widgetName
            typeField put "Индикатор"
            dataSourceField put "Обороты двигателя Магадан 636.3 123 (запрос по ОП OQL)"
            valueSourceTypeField put "Значение"
            valueField put "Исправность"
            saveButton.makeClick()
            checkIn("сообщение об успешном создании виджета отображается") {
                assertNotNull(findMessageByText("Запись сохранена"))
            }
            approve("Да")
            checkIn("сообщение об успешном создании группы виджетов отображается") {
                assertNotNull(findMessageByText("Группа успешно создана"))
            }
        }
        WidgetsList {
            searchField put widgetName
            checkIn("'$widgetName' появился в списке виджетов") {
                assertNotNull(findItemInWidgetListByText(widgetName, content))
            }
        }
    }

    @Feature("Создание виджетов")
    @Story("Успешное создание виджета типа Карта со статистикой")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun statisticsMapWidgetCreationTest() {

        val widgetName = "Тестовый виджет".withRandomPostfix()

        MainMenu {
            nowIs(visible)
            go("Администрирование", "Виджеты")
        }
        WidgetsList {
            openBlock()
            addToBlockButton.makeClick()
        }
        WidgetEditForm {
            nameField put widgetName
            typeField put "Карта с статистикой"
            dataSourceField put "Заселенность по регистрации (sql)"
            mapTypeField put "Субъекты федерации РФ"
            regionCodeField put "Код региона"
            valueForColorCalculationField put "Количество проживающих"
            getMinValueFromSourceField.makeClick()
            minColorValueField put "0"
            getMaxValueFromSourceField.makeClick()
            maxColorValueField put "10"
            intervalCountField put "10"
            addButton.makeClick()
        }
        CaptionSelectionFormForStatisticsMap {
            captionField put "Название региона"
            valueField put "Название региона"
            saveButton.makeClick()
        }
        WidgetEditForm {
            saveButton.makeClick()
            checkIn("сообщение об успешном создании виджета отображается") {
                assertNotNull(findMessageByText("Запись сохранена"))
            }
            approve("Да")
            checkIn("сообщение об успешном создании группы виджетов отображается") {
                assertNotNull(findMessageByText("Группа успешно создана"))
            }
        }
        WidgetsList {
            searchField put widgetName
            checkIn("'$widgetName' появился в списке виджетов") {
                assertNotNull(findItemInWidgetListByText(widgetName, content))
            }
        }
    }

    @Feature("Создание виджетов")
    @Story("Успешное создание виджета типа Пустой")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun emptyWidgetCreationTest() {

        val widgetName = "Тестовый виджет".withRandomPostfix()

        MainMenu {
            go("Администрирование", "Виджеты")
        }
        WidgetsList {
            openBlock()
            addToBlockButton.makeClick()
        }
        WidgetEditForm {
            nameField put widgetName
            typeField put "Пустой"
            saveButton.makeClick()
            checkIn("сообщение об успешном создании виджета отображается") {
                assertNotNull(findMessageByText("Запись сохранена"))
            }
            approve("Да")
            checkIn("сообщение об успешном создании группы виджетов отображается") {
                assertNotNull(findMessageByText("Группа успешно создана"))
            }
        }
        WidgetsList {
            searchField put widgetName
            checkIn("'$widgetName' появился в списке виджетов") {
                assertNotNull(findItemInWidgetListByText(widgetName, content))
            }
        }
    }

    @Feature("Создание виджетов")
    @Story("Успешное создание виджета типа Задачи ВРМ")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun brmWidgetCreationTest() {

        val widgetName = "Тестовый виджет".withRandomPostfix()

        MainMenu {
            go("Администрирование", "Виджеты")
        }
        WidgetsList {
            openBlock()
            addToBlockButton.makeClick()
        }
        WidgetEditForm {
            nameField put widgetName
            typeField put "Задачи BPM"
            saveButton.makeClick()
            checkIn("сообщение об успешном создании виджета отображается") {
                assertNotNull(findMessageByText("Запись сохранена"))
            }
            approve("Да")
            checkIn("сообщение об успешном создании группы виджетов отображается") {
                assertNotNull(findMessageByText("Группа успешно создана"))
            }
        }
        WidgetsList {
            searchField put widgetName
            checkIn("'$widgetName' появился в списке виджетов") {
                assertNotNull(findItemInWidgetListByText(widgetName, content))
            }
        }
    }
}