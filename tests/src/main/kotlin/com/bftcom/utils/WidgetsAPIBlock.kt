package com.bftcom.utils

import com.bftcom.configurator.commons.DataType
import com.bftcom.ice.common.Role
import com.bftcom.ice.common.maps.DataMapF
import com.bftcom.saumi.testapi.*
import com.bftcom.widgets.common.*
import com.bftcom.widgets.store.common.*
import io.qameta.allure.Step

object WidgetsAPI {
    operator fun invoke(builder: WidgetsAPI.() -> Unit): WidgetsAPI {
        builder(this)
        return this
    }
}

@Step("Сборка параметра запроса")
fun constructRequestParam(paramName: String, paramDisplayName: String): DataMapF<ParameterAlias> {

    val param = ParameterAlias.create {
        it[type] = ParameterAliasType.PARAMETER
        it[name] = paramName
        it[title] = paramDisplayName
        it[dataType] = DataType.BOOLEAN
        it[operator] = SqlOperator.EQ
        it[hide] = false
        it[hideOperator] = true
        it[defaultValue] = DefaultValue.create {
            it[value] = "true"
        }
    }
    return param
}

@Step("Сборка источника данных")
fun constructDataSource(dataSourceDisplayName: String, dataSourceType: String, token: String): DataMapF<DataSource> {

    val appObject11 = AppObjectApi(token).getAppObject("COW.cow")

    val dataSource = DataSource.create {
        it[displayName] = dataSourceDisplayName

        when (dataSourceType) {

            ObjectModelSource.displayName -> {
                it[type] = ObjectModelSource.name
                it[data] = ObjectModelSource.create {
                    it[appObject] = AppObjectField.create {
                        it[appObject] = appObject11
                    }
                    it[selectedFields] = "name"
                }
            }

            SqlSource.displayName -> {
                it[type] = SqlSource.name
                it[data] = SqlSource.create {
                    it[query] = "new query"
                }
            }
        }
    }
    return dataSource
}

@Step("Сборка виджета")
fun constructWidget(widgetName: String, widgetTypeValue: String, token: String, dataSourceName: String? = null): DataMapF<WidgetObject> {
    val widget = WidgetObject.create {
        it[displayName] = widgetName
        it[descriptionHtml] = ""
        it[showParamValues] = false
        it[includeGlobal] = false
        it[includeVisible] = false

        when (widgetTypeValue) {

            ClockWidgetField.displayName -> {
                it[widgetType] = ClockWidgetField.name
                it[source] = constructDataSource("", SqlSource.displayName, token)
                it[childFields] = ClockWidgetField.create {
                    it[mode] = ClockDisplayMode.ONLY_TIME
                }
            }

            ObjectListField.displayName -> {
                it[widgetType] = ObjectListField.name
                it[source] = constructDataSource(dataSourceName!!, ObjectModelSource.displayName, token)
                it[childFields] = ObjectListField.create {
                    it[pageSize] = 10
                }
            }
        }
    }
    return widget
}

@Step("Конструктор уровня виджетов")
fun constructWidgetLevel(
    widgetsNames: List<String>,
    widgetType: String? = null,
    token: String? = null,
    widInst: WidgetApi? = null,
    dataSourceName: String? = null
): MutableList<DataMapF<WidgetLevel>> {

    val widgetsList = mutableListOf<DataMapF<WidgetLevel>>()

    var wid: DataMapF<WidgetObject>
    for (i in 0 until widgetsNames.size) {
        wid = if (widgetType != null) {
            constructWidget(widgetsNames[i], widgetType, token!!, dataSourceName)
        } else {
            widInst!!.getWidget(widgetsNames[i])
        }

        val widLevel = WidgetLevel.create {
            it[widget] = wid
            it[level] = i + 1
            it[mark] = "mark"
        }
        widgetsList.add(widLevel)
    }
    return widgetsList
}

@Step("Сборка группы виджетов")
fun constructGroup(groupName: String, widgetsList: MutableList<DataMapF<WidgetLevel>>? = null): DataMapF<WidgetGroup> {
    val widgetGroup = WidgetGroup.create {
        it[displayName] = groupName
        it[roles].addAll(
            listOf(
                WidgetGroupRole.create {
                    it[role] = Role.createInSilence { it[id] = 0 }
                }
            )
        )
        it[active] = true
        it[xSpan] = 1
        it[showTitle] = true
        it[getTitleFromWidget] = false
        it[canShowDialog] = true
        //it[titleHtml] = ""
        //it[descriptionHtml] = ""
        if (widgetsList != null) {
            it[widgetLevels] = WidgetGroupLevel.create {
                //списочные поля заполняем вот так - addAll(...)
                it[levels].addAll(widgetsList)
            }
        }
    }
    return widgetGroup
}

@Step("Сборка дашборды")
fun constructDashboard(
    dashboardDisplayName: String,
    groupLevel: Collection<DataMapF<DashboardWidgetGroup>>,
    token: String,
    userName: String
): DataMapF<Dashboard> {

    val dashboard = Dashboard.create {
        it[displayName] = dashboardDisplayName
        it[user] = UserApi(token).getUser(userName)
        it[active] = true
        it[isDefault] = false
        it[keepParamInHistory] = true
        it[groups].addAll(groupLevel)
    }
    return dashboard
}

@Step("Создание параметра запроса")
fun createRequestParam(paramName: String, paramDisplayName: String) {
    val token = AuthApi.getToken("root", "root")
    val dsInst = DataSourceApi(token)

    val param = constructRequestParam(paramName, paramDisplayName)
    dsInst.saveParameter(param)

}

@Step("Создание источника данных без параметров через API")
fun createDataSourceWithoutParams(dataSourceName: String, dataSourceType: String) {
    val token = AuthApi.getToken("root", "root")
    val dsInst = DataSourceApi(token)

    val dataSource = constructDataSource(dataSourceName, dataSourceType, token)
    dsInst.saveDataSource(dataSource)
}

@Step("Создание виджета через API")
fun createWidget(widgetName: String, widgetType: String, dataSourceName: String? = null): String {
    val token = AuthApi.getToken("root", "root")
    val w = WidgetApi(token)

    val widget = constructWidget(widgetName, widgetType, token, dataSourceName)

    return w.saveWidget(widget, true)
}

@Step("Создание группы без виджетов через API")
fun createWidgetGroupWithoutWidgets(groupName: String) {
    val token = AuthApi.getToken("root", "root")
    val w = WidgetApi(token)

    val wg = constructGroup(groupName)
    w.saveWidgetGroup(wg, true)
}

@Step("Создание группы виджетов с виджетами через API")
fun createWidgetGroupWithNewWidgets(groupName: String, widgets: List<String>, widgetType: String) {
    val token = AuthApi.getToken("root", "root")
    val widInst = WidgetApi(token)

    val widgetsList = constructWidgetLevel(widgets, widgetType, token)
    val widgetGroup = constructGroup(groupName, widgetsList)

//сохраняем. в ответ на успешный запрос придет id только что созданного объекта
//флаг cascadeCreate говорит о том, что все вложенные сущности (например WidgetObject)
//будут созданы в процессе сохраенния корневой сущнсоти
// если флаг false - то должны быть заданы id для сложенных сущностей (иначе упадет)
// они будут просто подтянуты из бд

    widInst.saveWidgetGroup(widgetGroup, true)
}

@Step("Создание группы виджетов с существующими виджетами через API")
fun createWidgetGroupWithExistingWidgets(groupName: String, widgets: List<String>, dataSourceName: String? = null) {
    val token = AuthApi.getToken("root", "root")
    val widInst = WidgetApi(token)

    val widgetsList = constructWidgetLevel(widgets, null, null, widInst, dataSourceName)
    val widgetGroup = constructGroup(groupName, widgetsList)

    widInst.saveWidgetGroup(widgetGroup, false)
}

@Step("Создание дашборды с виджетами")
fun createDashboardWithGroups(
    dashboardDisplayName: String,
    groupName: String,
    widgets: List<String>,
    widgetType: String,
    userName: String,
    dataSourceName: String? = null
) {
    val token = AuthApi.getToken("root", "root")
    val dbInst = DashboardApi(token)

    val widgetsList = constructWidgetLevel(widgets, widgetType, token, null, dataSourceName)
    val widgetGroupCreated = constructGroup(groupName, widgetsList)

    val groupLevel = mutableListOf<DataMapF<DashboardWidgetGroup>>()
    val dashboardGroup = DashboardWidgetGroup.create {
        it[displayName] = groupName
        it[position] = 1
        it[widgetGroup] = widgetGroupCreated
        it[tag] = "label".withRandomPostfix()
    }
    groupLevel.add(dashboardGroup)

    val dashboard = constructDashboard(dashboardDisplayName, groupLevel, token, userName)

    dbInst.saveDashboard(dashboard, true)
}

@Step("Создание источника данных с параметрами через API")
fun createDataSourceWithParams(dataSourceName: String) {

}

