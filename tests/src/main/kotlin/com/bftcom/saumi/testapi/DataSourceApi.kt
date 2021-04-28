package com.bftcom.saumi.testapi

import com.bftcom.ice.common.maps.DataMapF
import com.bftcom.ice.common.utils.GUID
import com.bftcom.widgets.common.DataSource
import com.bftcom.widgets.common.ParameterAlias

class DataSourceApi(accessToken: String) : DataMapTestApi(accessToken) {

    fun getDataSource(id: Long): DataMapF<DataSource> {
        return getDataMapRequest("api/test/datasource", id)
    }

    fun saveDataSource(dm: DataMapF<DataSource>): String {
        return postSaveRequest("api/test/datasource/save", dm, false)
    }

    fun getParameter(id: Long): DataMapF<ParameterAlias> {
        return getDataMapRequest("api/test/datasource/parameter", id)
    }

    fun saveParameter(dm: DataMapF<ParameterAlias>): String {
        return postSaveRequest("api/test/datasource/parameter/save", dm, false)
    }

    fun deleteDataSource(id: GUID): Boolean {
        return postDeleteRequest("api/test/datasource/delete", id._uuid)
    }

    fun deleteParameter(id: GUID): Boolean {
        return postDeleteRequest("api/test/datasource/parameter/delete", id._uuid)
    }
}