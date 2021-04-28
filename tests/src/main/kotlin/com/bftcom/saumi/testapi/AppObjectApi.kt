package com.bftcom.saumi.testapi

import com.bftcom.configurator.commons.AppObject
import com.bftcom.ice.common.maps.DataMapF

class AppObjectApi(accessToken: String) : DataMapTestApi(accessToken) {

    fun getAppObject(id: String): DataMapF<AppObject> {
        return getDataMapRequest("api/test/appobject", id)
    }
}