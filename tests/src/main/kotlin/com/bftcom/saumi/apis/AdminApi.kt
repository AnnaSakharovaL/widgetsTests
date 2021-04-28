package com.bftcom.saumi.apis

import com.bftcom.saumi.pages.RightDropMenu
import com.bftcom.saumi.pages.RightMenu
import com.bftcom.saumi.pages.UsersApp
import com.bftcom.utils.*
import com.codeborne.selenide.Condition
import io.qameta.allure.Step
import pages.Login
import pages.MainMenu

class User(
    val login: String,
    val surname: String,
    val firstName: String,
    val security: String,
    email: String,
    phone: String,
    roles: String,
    val password: String
) {

    constructor(data: Map<String, String>) : this(
        data.getValue("Логин"),
        data.getValue("Фамилия"),
        data.getValue("Имя"),
        data.getValue("Политика безопасности"),
        data.getValue("Электронная почта"),
        data.getValue("Телефон"),
        data.getValue("Роли"),
        data.getValue("Пароль")
    )

    override fun toString(): String {
        return "${login} , ${surname}"
    }
}

class OrgStructure(val name: String, val typeOfOrgStructure: String, val parentName: String? = null) {

    constructor(data: Map<String, String>) : this(
        data.getValue("Полное наименование"),
        data.getValue("Тип"),
        data.getValue("Вышестоящее подразделение")
    )

    fun toMap(): Map<String, String> {
        val data = mutableMapOf<String, String>()
        name.let { data["Полное наименование"] = it }
        typeOfOrgStructure.let { data["Тип"] = it }
        parentName?.takeIf { it.isBlank() }?.let { data["Вышестоящее подразделение"] = it }
        return data
    }
}

object AdminApi {

    @Step("Создание случайного пользователя через api")
    fun createRandomUserByIce(): User {
        val userData = mapOf(
            "Логин" to Randoms.letters(10),
            "Фамилия" to Randoms.ruLetters(10),
            "Имя" to Randoms.ruLetters(10),
            "Политика безопасности" to "DEFAULT",
            "Электронная почта" to "1",
            "Телефон" to "1",
            "Роли" to "Супер пользователь",
            "Пароль" to "123",
            "Подтвердите пароль" to "123"
        )
        /*println("@@@@@@@@@@@@@@")

        runBlocking {
            val ss = UserAccount {
                it[name] = userData["Логин"]
                it[fullName] = userData["Фамилия"]
                it[fullName] = it[fullName] + userData["Имя"]
                // it[userPolicy] = JsDataService.find(UserPolicy.on().full().filter { f(UserPolicy.name) eq userData["Политика безопасности"].toString() }) //userData["Политика безопасности"] //UserPolicy UserPolicy{}
                it[email] = userData["Электронная почта"]
                it[phone] = userData["Телефон"]
                //it[userRoles] = UserRole {} // userData["Роли"]
                it[password] = userData["Пароль"]
            }

            ss.toJson()

            val buckets: List<DeltaBucket> = DeltaStore.collectBuckets()
            println("buckets ${buckets.size}")

            buckets.forEach {
                println(">>> ${it}")
            }
            throw IllegalStateException("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&")
            //val obj = buckets.filter { it.deltas.isNotEmpty() }.map { buildObjectFromDeltaBucket(it) }
            //val req = JSON.stringify(obj)
            //val json = httpPost(req, "/api/saveChanges")

            //JsDataService.flushAllClientChanges()
        }*/

        return User(userData)
    }

    @Step("Создание случайного пользователя через api")
    fun createRandomUser(): User {
        val userData = mapOf(
            "Логин" to Randoms.letters(10),
            "Фамилия" to Randoms.ruLetters(10),
            "Имя" to Randoms.ruLetters(10),
            "Политика безопасности" to "DEFAULT",
            "Электронная почта" to "1",
            "Телефон" to "1",
            "Роли" to "Супер пользователь",
            "Пароль" to "123",
            "Подтвердите пароль" to "123"
        )

        MainMenu {
            nowIsNot(Condition.exist)
        }
        Login {
            nowIs(Condition.appear)
            auth("root", "root")
            nowIsNot(Condition.appear)
        }
        MainMenu {
            nowIs(Condition.appear)
            go("Администрирование", "Пользователи")
        }
        UsersApp {
            createUser(userData)
        }
        RightMenu {
            nowIs(Condition.appear)

            rightMenu.openInList()

            RightDropMenu {
                nowIs(Condition.appear)

                logoutMenu.openInList()

                nowIs(Condition.disappear)
            }

            nowIs(Condition.disappear)
        }
        MainMenu {
            nowIsNot(Condition.exist)
        }
        waitLoadJs()
        Login {
            nowIs(Condition.exist)
        }

        return User(userData)
    }
}
