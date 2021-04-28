package com.bftcom.utils

import com.bftcom.ice.common.UserAccount
import com.bftcom.ice.common.UserRole
import com.bftcom.ice.common.maps.DataMapF
import com.bftcom.saumi.testapi.AuthApi
import com.bftcom.saumi.testapi.UserApi
import io.qameta.allure.Step
import org.springframework.web.client.HttpClientErrorException

@Step("Создание пользователя")
fun createUser(userName: String, userPassword: String) {
    val token = AuthApi.getToken("root", "root")
    val userRole = UserApi(token).getRole("superUser")

    val userData = UserAccount.create {
        it[fullName] = userName
        it[name] = userName
        it[password] = userPassword
        it[recieveSms] = false
        it[recieveEmails] = false
        it[userRoles].add(UserRole.create {
            it[role] = userRole
        })
    }
    UserApi(token).saveUser(userData)
}

@Step("Создание пользователя {0}")
fun deleteUser(userLogin: String) {
    val token = AuthApi.getToken("root", "root")
    UserApi(token).deleteUser(userLogin)
}

@Step("Получение пользователя {0}")
fun getUser(userLogin: String): DataMapF<UserAccount> {
    val token = AuthApi.getToken("root", "root")
    return UserApi(token).getUser(userLogin)
}

@Step("Проверка и создание пользователя в случае его отсутствия")
fun checkAndCreateUser(userLogin: String, userPassword: String) {
    try {
        getUser(userLogin)
    } catch (e: HttpClientErrorException) {
        createUser(userLogin, userPassword)
    }
}