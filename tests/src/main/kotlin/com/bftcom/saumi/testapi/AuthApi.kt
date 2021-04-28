package com.bftcom.saumi.testapi

import org.json.JSONObject
import org.springframework.http.*

object AuthApi : BaseTestApi() {
    fun getToken(name: String, password: String): String {
        val request = JSONObject()
        request.put("username", name)
        request.put("password", password)
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json; charset=utf-8")

        val entity = HttpEntity(request.toString(), headers)

        val loginResponse: ResponseEntity<String> = restTemplate()
            .exchange("$url/api/auth/login", HttpMethod.POST, entity, String::class.java)
        return if (loginResponse.statusCode == HttpStatus.OK) {
            val userJson = JSONObject(loginResponse.body)
            userJson["accessToken"].toString()
        } else throw IllegalAccessException("can't login")
    }
}