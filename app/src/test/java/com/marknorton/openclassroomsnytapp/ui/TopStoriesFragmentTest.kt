package com.marknorton.openclassroomsnytapp.ui


import com.google.gson.JsonObject
import com.google.gson.JsonParser
import junit.framework.TestCase.assertEquals
import org.junit.Assert
import org.junit.Test
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Created by Mark Norton on 12/22/2020.
 */
class TopStoriesFragmentTest {

    @Test
    fun testTopStoriesJSONApi() {
        val filename = "topstories_fragment_sample_data.json"
        val inputStream = readFileWithNewLineFromResources(filename)

        val jsonObject: JsonObject = JsonParser().parse(inputStream).asJsonObject
        Assert.assertTrue(jsonObject.isJsonObject)
        Assert.assertTrue(jsonObject.get("status").asString == "OK")
    }

    @Throws(IOException::class)
    fun readFileWithNewLineFromResources(fileName: String): String {
        var inputStream: InputStream? = null
        try {
            inputStream =
                javaClass.classLoader?.getResourceAsStream(fileName)
            val builder = StringBuilder()
            val reader = BufferedReader(InputStreamReader(inputStream))

            var theCharNum = reader.read()
            while (theCharNum != -1) {
                builder.append(theCharNum.toChar())
                theCharNum = reader.read()
            }

            return builder.toString()
        } finally {
            inputStream?.close()
        }
    }

}
