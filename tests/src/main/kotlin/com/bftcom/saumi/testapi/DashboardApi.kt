package com.bftcom.saumi.testapi

import com.bftcom.ice.common.maps.DataMapF
import com.bftcom.ice.common.utils.GUID
import com.bftcom.widgets.common.Dashboard

class DashboardApi(accessToken: String) : DataMapTestApi(accessToken) {

    fun getDashboard(id: GUID): DataMapF<Dashboard> {
        return getDataMapRequest("api/test/dashboard", id._uuid)
    }

    fun saveDashboard(dm: DataMapF<Dashboard>, cascadeCreate: Boolean = false): String {
        return postSaveRequest("api/test/dashboard/save", dm, cascadeCreate)
    }

    fun deleteDashboard(id: GUID): Boolean {
        return postDeleteRequest("api/test/dashboard/delete", id._uuid)
    }
}