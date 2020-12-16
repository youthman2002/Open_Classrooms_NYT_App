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
import org.jetbrains.anko.onComplete
import org.json.JSONObject
import java.net.URL
import java.util.*

class MostPopularFragment : Fragment() {
    private var urls = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_top_stories, container, false)
        doAsync {
            urls =
                (URL("https://api.nytimes.com/svc/mostpopular/v2/viewed/30.json?api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd").readText())
            onComplete {
                val db = Database(context!!)
                val returnList = ArrayList<ArticleModel>()
                val jsonObject = JSONObject(urls)
                val jsonObjects = jsonObject.getJSONArray("results")

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

                    val imageurl = c.getJSONArray("media")
                    for (j in 0 until imageurl.length()) {
                        val e = imageurl.getJSONObject(j)

                        val media = e!!.getJSONArray("media-metadata")

                        for (k in 0 until media.length()) {
                            val d = media.getJSONObject(k)
                            Log.d("Log", "Log-MEDIA-METADATA: $d")
                            val subtype = d.getString("format")
                            if (subtype == "Standard Thumbnail") {
                                image = (d.getString("url"))
                            }
                        }
                    }
                    db.addHeadline(theHeadline)
                    returnList.add(ArticleModel(section, image, theHeadline, pubDate, url))
                    image = ""
                }


                val recyclerview = rootView.findViewById(R.id.rvTopStories) as RecyclerView
                recyclerview.layoutManager = LinearLayoutManager(activity)
                recyclerview.adapter = ArticleAdapter(returnList, context!!)
            }
        }
        return rootView
    }

}


