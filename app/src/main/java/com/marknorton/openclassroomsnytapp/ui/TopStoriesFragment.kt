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
import com.marknorton.openclassroomsnytapp.Database
import com.marknorton.openclassroomsnytapp.R
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL
import java.util.*

class TopStoriesFragment : Fragment() {
    private var urls = ""
 private val dbName = "NYTDatabase"
 private val dbVersion = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_top_stories, container, false)
        doAsync {
            urls = (URL("https://api.nytimes.com/svc/topstories/v2/science.json?api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd").readText())
        }
        val db = Database(context!!, dbVersion)

        val returnList = ArrayList<ArticleModel>()
        Thread.sleep(2500)

        //TODO  Add OnSuccessListener


        val jsonObject = JSONObject(urls)
        val jsonObjects = jsonObject.getJSONArray("results")

        //       var jsonObjects = (jsonObject?.getJSONArray("docs"))

        var image = ""
        var url: String
        var pubDate: String
        var section: String
        var theHeadline: String

        for (i in 0 until jsonObjects.length()) {
            val c = jsonObjects.getJSONObject(i)
            section = (c!!.getString("section"))
            theHeadline = (c.getString("title"))
            url = (c.getString("url"))
            pubDate = (c.getString("published_date"))


            val imageurl = c.getJSONArray("multimedia")
            for (j in 0 until imageurl.length()) {
                val d = imageurl.getJSONObject(j)
                val subtype = d.getString("format")
                if (subtype == "Standard Thumbnail") {
                    image = (d.getString("url"))
                }
            }

            db.addHeadline(theHeadline)

            returnList.add(ArticleModel(section, image, theHeadline, pubDate, url))
            Log.d("Log", "Log-INFO: $section, $image, $theHeadline, $pubDate")
            image = ""
        }

//        val rootView = inflater.inflate(R.layout.fragment_top_stories, container, false)
            val recyclerview = rootView.findViewById(R.id.rvTopStories) as RecyclerView
            recyclerview.layoutManager = LinearLayoutManager(activity)
            recyclerview.adapter = ArticleAdapter(returnList, context!!)
            return rootView
            //     }
            //     return rootView
        }

}






