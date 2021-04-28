package com.bftcom.saumi.testapi

import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import utils.PropertyLoader

open class BaseTestApi {
    //protected val url = "http://localhost:8080/app"
    protected val url = PropertyLoader.loadProperty("baseURL")

    protected fun restTemplate(): RestTemplate {
        val httpClient = HttpClientBuilder.create().build()
        val httpRequestFactory = HttpComponentsClientHttpRequestFactory(httpClient)
        return RestTemplate(httpRequestFactory)
    }
}

data class SaveResponse(
    val id: Any? = null,
    val error: String? = null
)