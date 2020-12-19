package com.marknorton.openclassroomsnytapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marknorton.openclassroomsnytapp.ArticleAdapter
import com.marknorton.openclassroomsnytapp.ArticleModel
import com.marknorton.openclassroomsnytapp.R
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import org.json.JSONObject
import java.net.URL
import java.util.*

class TechnologyFragment : Fragment() {
    var urls = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_top_stories, container, false)
        doAsync {
            urls =
                (URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=news_desk:(\"Technology\")&q=Science&api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd").readText())
            onComplete {
                val returnList = ArrayList<ArticleModel>()
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
                    Log.d("Log", "Log - imageurlJSON - " + imageurl.toString())
                    for (j in 0 until imageurl.length()) {
                        val d = imageurl.getJSONObject(j)
                        val subtype = d.getString("subtype")

                        if (subtype == "thumbnail") {
                            image = (d.getString("url"))
                            image = "https://www.nytimes.com/$image"
                        }
                    }
                    val headline = c.getJSONObject("headline")
                    theHeadline = (headline.getString("main"))
                    pubDate = (c.getString("pub_date"))
                    url = (c.getString("web_url"))

                    returnList.add(ArticleModel(section, image, theHeadline, pubDate, url))
//            Log.d("Log", "Log-INFO: $section, $image, $theHeadline, $pubDate, $url")
                    image = ""
                }


                val recyclerview = rootView.findViewById(R.id.rvTopStories) as RecyclerView
                recyclerview.layoutManager = LinearLayoutManager(context)
                recyclerview.adapter = ArticleAdapter(returnList, context!!)
            }
        }
        return rootView
    }


}
