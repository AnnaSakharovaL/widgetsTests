package com.bftcom.saumi.testapi

import com.bftcom.ice.common.maps.DataMapF
import com.bftcom.ice.common.utils.GUID
import com.bftcom.widgets.common.WidgetGroup
import com.bftcom.widgets.common.WidgetObject

class WidgetApi(accessToken: String) : DataMapTestApi(accessToken) {

    fun getWidget(id: String): DataMapF<WidgetObject> {
        return getDataMapRequest("api/test/widget", id)
    }

    fun getWidgetGroup(id: GUID): DataMapF<WidgetGroup> {
        return getDataMapRequest("api/test/widget/group", id._uuid)
    }

    fun saveWidget(dm: DataMapF<WidgetObject>, cascadeCreate: Boolean = false): String {
        return postSaveRequest("api/test/widget/save", dm, cascadeCreate)
    }

    fun saveWidgetGroup(dm: DataMapF<WidgetGroup>, cascadeCreate: Boolean): String {
        return postSaveRequest("api/test/widget/group/save", dm, cascadeCreate)
    }

    fun deleteWidget(id: GUID): Boolean {
        return postDeleteRequest("api/test/widget/delete", id._uuid)
    }

    fun deleteWidgetGroup(id: GUID): Boolean {
        return postDeleteRequest("api/test/widget/group/delete", id._uuid)
    }
}