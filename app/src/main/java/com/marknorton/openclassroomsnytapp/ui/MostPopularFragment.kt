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


class MostPopularFragment : Fragment() {
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
                (URL("https://api.nytimes.com/svc/mostpopular/v2/shared/1/facebook.json?api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd").readText())
        }
        var returnList = ArrayList<ArticleModel>()
        Thread.sleep(2000)
        var jsonObject: JSONObject? = JSONObject(urls)
        var jsonObjects = jsonObject?.getJSONArray("results")

        //       var jsonObjects = (jsonObject?.getJSONArray("docs"))

        var image = ""
        var url = ""
        var pubDate = ""
        var section = ""
        var theHeadline = ""

        for (i in 0 until jsonObjects!!.length()) {
            val c = jsonObjects?.getJSONObject(i)
            section = (c!!.getString("section"))
            theHeadline = (c!!.getString("title"))
            url = (c!!.getString("url"))
            pubDate = (c!!.getString("published_date"))


            var imageurl = c!!.getJSONArray("media")
            for (j in 0 until imageurl!!.length()) {
                val e = imageurl?.getJSONObject(j)
                Log.d("Log", "Log-MEDIA: $e")
                var media = e!!.getJSONArray("media-metadata")

                for (k in 0 until media!!.length()) {
                    val d = media?.getJSONObject(k)
                    Log.d("Log", "Log-MEDIA-METADATA: $d")
                    val subtype = d.getString("format")
                    if (subtype == "Standard Thumbnail") {
                        image = (d.getString("url"))
                    }
                }
          }
            returnList.add(ArticleModel(section, image, theHeadline, pubDate, url))
            Log.d("Log", "Log-INFO: $section, $image, $theHeadline, $pubDate")
            image = ""
        }


        val recyclerview = rootView.findViewById(R.id.rvTopStories) as RecyclerView
        recyclerview.layoutManager = LinearLayoutManager(activity)
        recyclerview.adapter = ArticleAdapter(returnList, getContext()!!)
        return rootView
        //     }
        //     return rootView
    }

}


