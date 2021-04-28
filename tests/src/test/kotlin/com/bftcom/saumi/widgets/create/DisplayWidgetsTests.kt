package com.bftcom.saumi.widgets.create

import com.bftcom.utils.*
import io.qameta.allure.*
import org.junit.Assert.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import pages.DashboardMainPage
import pages.MainMenu
import utils.PropertyLoader

@Execution(ExecutionMode.CONCURRENT)
@Epic("Виджеты")
class DisplayWidgetsTests : ForTests() {

    val userLogin = "root"
    val userPassword = "root"

    @BeforeEach
    fun beforeEach() {
        login(userLogin, userPassword)
    }

    @Feature("Отображение виджетов")
    @Story("Успешное отображение пустого виджета")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun emptyWidgetDisplayTest() {

        MainMenu {
            go("Рабочие панели", "Главная страница")
        }
        DashboardMainPage {
            workPanelSelector put "Проверка отображения пустого виджета"

            checkIn("пустой виджет отображается корректно") {
                assertTrue(widgetCard.isSamePatternLike(PropertyLoader.loadProperty("emptyWidgetFile")))
            }
        }
    }

    @Feature("Отображение виджетов")
    @Story("Успешное отображение виджета типа Географическая карта")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun mapWidgetDisplayTest() {

        MainMenu {
            go("Рабочие панели", "Главная страница")
        }
        DashboardMainPage {
            workPanelSelector put "Проверка отображения географической карты"

            checkIn("виджет типа 'Географическая карта' отображается корректно") {
                assertTrue(widgetCard.isSamePatternLike(PropertyLoader.loadProperty("mapWidgetFile")))
            }
        }
    }

    @Feature("Отображение виджетов")
    @Story("Успешное отображение виджета типа Спидометр")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun speedometrWidgetDisplayTest() {

        MainMenu {
            go("Рабочие панели", "Главная страница")
        }
        DashboardMainPage {
            workPanelSelector put "Проверка отображения спидометра"
            checkFilter()

            checkIn("виджет типа 'Спидометр' отображается корректно") {
                assertTrue(widgetCard.isSamePatternLike(PropertyLoader.loadProperty("speedometrWidgetFile")))
            }
        }
    }

    @Feature("Отображение виджетов")
    @Story("Успешное отображение виджета 'Подготовка космонавтов'")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun ganttCosmonautPreparationDisplayTest() {

        MainMenu {
            go("Рабочие панели", "Главная страница")
        }
        DashboardMainPage {
            workPanelSelector put "Подготовка космонавтов"

            openSubtasks()

            checkIn("виджет 'Подготовка космонавтов' отображается корректно") {
                assertTrue(widgetCard.isSamePatternLike(PropertyLoader.loadProperty("ganttCosmonautPreparationFile")))
            }
        }
    }

    @Feature("Отображение виджетов")
    @Story("Успешное отображение виджета 'Разработка космолета'")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun ganttSpacecraftDevelopmentDisplayTest() {

        MainMenu {
            go("Рабочие панели", "Главная страница")
        }
        DashboardMainPage {
            workPanelSelector put "Разработка космолета"

            openSubtasks()

            checkIn("виджет 'Подготовка космонавтов' отображается корректно") {
                assertTrue(widgetCard.isSamePatternLike(PropertyLoader.loadProperty("ganttCosmonautPreparationFile")))
            }
        }
    }
}