package com.bftcom.saumi.testapi

import com.bftcom.ice.common.Role
import com.bftcom.ice.common.UserAccount
import com.bftcom.ice.common.maps.DataMapF

class UserApi(accessToken: String) : DataMapTestApi(accessToken) {

    fun getUser(login: String): DataMapF<UserAccount> {
        return getDataMapRequest("api/test/user", login)
    }

    fun getRole(code: String): DataMapF<Role> {
        return getDataMapRequest("api/test/user/role", code)
    }

    /**
     * Чтобы создать ползователя, сначало нужно запросить роль
     *  val r = UserApi.getRole("admin")
     *  минимуи необходимых полей:
     *   val wg = UserAccount.create {
     *           it[fullName] = "user"
     *           it[name] = "user"
     *           it[password] = "user"
     *           it[recieveSms] = false
     *           it[recieveEmails] = false
     *           it[userRoles].add(UserRole.create {
     *           it[role] = r
     *       })
     *   }
     */
    fun saveUser(dm: DataMapF<UserAccount>): Boolean {
        return postSaveRequest("api/test/user/save", dm)
    }

    fun deleteUser(login: String): Boolean {
        return postDeleteRequest("api/test/user/delete", login)
    }
}