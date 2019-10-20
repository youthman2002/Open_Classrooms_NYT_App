package com.marknorton.openclassroomsnytapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marknorton.openclassroomsnytapp.ArticleAdapter
import com.marknorton.openclassroomsnytapp.ArticleModel
import com.marknorton.openclassroomsnytapp.R
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL
import java.util.*




class SearchFragment : Fragment() {
    var urls = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_top_stories, container, false)
        doAsync {
            urls =
                (URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?q=Entrepreneurs&api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd").readText())
        }
        var returnList = ArrayList<ArticleModel>()
        Thread.sleep(2000)
        var jsonObject: JSONObject? = JSONObject(urls)
        jsonObject = jsonObject?.getJSONObject("response")

        var jsonObjects = (jsonObject?.getJSONArray("docs"))

        var image = ""
        var url =""
        var pubDate = ""
        var section = ""
        var theHeadline = ""

        for (i in 0 until jsonObjects!!.length()) {
            val c = jsonObjects?.getJSONObject(i)

            section = (c.getString("section_name"))

            var imageurl = c.getJSONArray("multimedia")
//            var imageurl = c.getJSONObject("multimedia")
            for (j in 0 until imageurl!!.length()) {
                val d = imageurl?.getJSONObject(j)
                val subtype = d.getString("subtype")
                //              Log.d("Log","Log-SUBTYPE:                    $subtype")
                if (subtype == "master315") {
//                    image.add(d.getString("url"))
                    image = (d.getString("url"))
//                    Log.d("Log","Log-IMAGE:                    $image")
                    //                   image = (d.getString("url"))
                    image = "https://www.nytimes.com/$image"
                }
            }
            var headline = c.getJSONObject("headline")
            theHeadline = (headline.getString("main"))
            pubDate = (c.getString("pub_date"))
            url = (c.getString("web_url"))

            returnList.add(ArticleModel(section, image, theHeadline, pubDate, url))
            Log.d(
                "Log", "Log-INFO: $section, $image, $theHeadline, $pubDate, $url"
            )
            image = ""
        }


        val recyclerview = rootView.findViewById(R.id.rvTopStories) as RecyclerView
        recyclerview.layoutManager = LinearLayoutManager(activity)
        recyclerview.adapter = ArticleAdapter(returnList)
        return rootView
        //     }
        //     return rootView
    }
}