package utils

import java.io.IOException
import java.util.*

object PropertyLoader {

    private const val PROPERTIES_FILE = "/application.properties"

    fun loadProperty(name: String?): String {

        val properties = Properties()
        try {
            properties.load(PropertyLoader::class.java.getResourceAsStream(PROPERTIES_FILE))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        var value = ""

        if (name != null) {
            value = properties.getProperty(name)
        }
        return value
    }

}
