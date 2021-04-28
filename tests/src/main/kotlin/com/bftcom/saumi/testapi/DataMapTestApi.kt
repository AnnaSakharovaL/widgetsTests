package com.bftcom.saumi.testapi

import com.bftcom.ice.common.maps.*
import com.bftcom.ice.server.util.dataMapFromJson
import com.bftcom.ice.server.util.toJson
import com.google.gson.Gson
import org.json.JSONObject
import org.springframework.http.*

open class DataMapTestApi(private val accessToken: String) : BaseTestApi() {
    private fun authHttpHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer $accessToken")
        headers.set("Content-Type", "application/json; charset=utf-8")
        return headers
    }

    private fun createSaveRequest(dm: DataMap, cascadeCreate: Boolean = false): String {
        val request = JSONObject()
        request.put("map", dm.toClearJson())
        request.put("cascadeCreate", cascadeCreate)
        return request.toString()
    }

    private fun DataMap.toClearJson(): String {
        return this.toJson(
            com.bftcom.ice.server.util.JsonWriteOptions(
                writeJsonTechProps = false,
                writeBackRefs = false,
                writeEntityName = false,
                writeSystemProps = false
            )
        )
    }

    @Throws(Exception::class)
    protected fun String?.getId(): String {
        val res = Gson().fromJson(this!!, SaveResponse::class.java)
        return try {
            val uuid = res.id as Map<*, *>
            uuid["_uuid"].toString()
        } catch (e: Throwable) {
            throw Exception(res.error)
        }
    }

    @Throws(Exception::class)
    protected fun postSaveRequest(apiUrl: String, dm: DataMap, cascadeCreate: Boolean): String {
        val entity = HttpEntity(createSaveRequest(dm, cascadeCreate), authHttpHeaders())
        val response = restTemplate().exchange("$url/$apiUrl", HttpMethod.POST, entity, String::class.java)
        return if (response.statusCode == HttpStatus.OK) {
            response.body.getId()
        } else throw Exception("response status ${response.statusCode}")
    }

    @Throws(Exception::class)
    protected fun postSaveRequest(apiUrl: String, dm: DataMap): Boolean {
        val entity = HttpEntity(dm.toClearJson(), authHttpHeaders())
        val response = restTemplate().exchange("$url/$apiUrl", HttpMethod.POST, entity, String::class.java)
        return if (response.statusCode == HttpStatus.OK) {
            true
        } else throw Exception("response status ${response.statusCode}")
    }

    @Throws(Exception::class)
    protected fun <T : MappingFieldSet<T>> getDataMapRequest(apiUrl: String, id: Any): DataMapF<T> {
        val entity = HttpEntity(null, authHttpHeaders())
        val response = restTemplate().exchange("$url/$apiUrl/$id", HttpMethod.GET, entity, String::class.java)
        return if (response.statusCode == HttpStatus.OK) {
            dataMapFromJson(response.body!!) as DataMapF<T>
        } else throw IllegalAccessException("can't get datamap")
    }

    @Throws(Exception::class)
    protected fun postDeleteRequest(apiUrl: String, id: String): Boolean {
        val entity = HttpEntity(null, authHttpHeaders())
        val response = restTemplate().exchange("$url/$apiUrl/$id", HttpMethod.POST, entity, String::class.java)
        return if (response.statusCode == HttpStatus.OK) {
            true
        } else throw Exception("response status ${response.statusCode}")
    }
}