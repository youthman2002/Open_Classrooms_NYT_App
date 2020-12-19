package com.marknorton.openclassroomsnytapp

import android.util.Log
import org.json.JSONObject
import java.util.ArrayList
import java.util.HashMap

/**
 *  Created by Mark Norton on 12/18/2020.
 *
 */
class DataParser {
    fun placeData(urls: String): HashMap<String, String> {
//        val db = Database(context!!)
        val placeData = HashMap<String, String>()
        val returnList = ArrayList<ArticleModel>()

        Log.d("Log", "Log - urls 2 - " + urls.toString())

        var jsonObject: JSONObject? = JSONObject(urls)
        jsonObject = jsonObject?.getJSONObject("response")

        val jsonObjects = (jsonObject?.getJSONArray("docs"))

        var image = ""
        var url: String
        var pubDate: String
        var section: String
        var theHeadline: String

        for (i in 0 until jsonObjects!!.length()) {
            val c = jsonObjects.getJSONObject(i)

            section = (c.getString("section_name"))

            val imageurl = c.getJSONArray("multimedia")
            for (j in 0 until imageurl.length()) {
                val d = imageurl.getJSONObject(j)
                val subtype = d.getString("subtype")

                if (subtype == "articleInline") {
                    image = (d.getString("url"))
                    image = "https://www.nytimes.com/$image"
                }
            }
            val headline = c.getJSONObject("headline")
            theHeadline = (headline.getString("main"))
            pubDate = (c.getString("pub_date"))
            url = (c.getString("web_url"))


            returnList.add(ArticleModel(section, image, theHeadline, pubDate, url))

/*            placeData["image"] = image
            placeData["theHeadline"] = theHeadline
            placeData["pubDate"] = pubDate
            placeData["url"] = url
            placeData["section"] = section
            image = ""
 */

        }


        return placeData
    }

}