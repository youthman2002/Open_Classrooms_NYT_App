package com.marknorton.openclassroomsnytapp.ui

// import com.bumptech.glide.Glide
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marknorton.openclassroomsnytapp.ArticleModel
import com.marknorton.openclassroomsnytapp.R
import com.marknorton.openclassroomsnytapp.TopArticleAdapter
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL
import java.util.*


class TopStoriesFragment : Fragment() {
    var urls = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //     inflater.inflate(R.layout.fragment_top_stories, container, false)

           val rootView = inflater.inflate(R.layout.fragment_top_stories, container, false)
        doAsync {
            //         val list = getAPIData() as ArrayList<ArticleModel>


            var result = ""
            Log.d("Log", "Log-In Try ")
//            val urls = (URL("https://api.nytimes.com/svc/topstories/v2/science.json?api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd").readText().toString())
            urls =
                (URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?q=Entrepreneurs&api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd").readText().toString())
            Log.d("Log", "Log-URL Data = $urls")
//            result = url
//            var list:ArrayList<ArticleModel> = processData(url)
        }
        var returnList = ArrayList<ArticleModel>()

Thread.sleep(2000)


        Log.d("Log", "Log-Preparing JSON")
//            var jsonObject: JSONObject? = JSONObject(result)
        var jsonObject: JSONObject? = JSONObject(urls)
        jsonObject = jsonObject?.getJSONObject("response")
        Log.d("Log", "Log-Processing JSON")
// Getting JSON Array node
        var jsonObjects = (jsonObject?.getJSONArray("docs"))
/*
        var image = mutableListOf<String>()
        var url = mutableListOf<String>()
        var pubDate = mutableListOf<String>()
        var section = mutableListOf<String>()
        var theHeadline = mutableListOf<String>()
*/
        var image =""
        var url =""
        var pubDate =""
        var section =""
        var theHeadline =""

        for (i in 0 until jsonObjects!!.length()) {
            val c = jsonObjects?.getJSONObject(i)
//            section.add(c.getString("section_name"))
            section = (c.getString("section_name"))
            Log.d("Log", "Log-URL SECTION = $section")
            var imageurl = c.getJSONArray("multimedia")
            for (j in 0 until imageurl!!.length()) {
                val d = imageurl?.getJSONObject(j)
                val subtype = d.getString("subtype")
                if (subtype == "thumbLarge") {
//                    image.add(d.getString("url"))
                    image = (d.getString("url"))
                    image = "https://www.nytimes.com/$image"
                    Log.d("Log", "Log-URL IMAGE = $image")
                }
            }
            var headline = c.getJSONObject("headline")
//            theHeadline.add(headline.getString("main"))
            theHeadline = (headline.getString("main"))
//            pubDate.add(c.getString("pub_date"))
            pubDate = (c.getString("pub_date"))
//            url.add(c.getString("web_url"))
            url = (c.getString("web_url"))

                returnList.add(ArticleModel(section, image, theHeadline, pubDate))
                Log.d(
                    "Log",
                    "Log-Section: $section, $image, $theHeadline, $pubDate"
                )
            }
Thread.sleep(2000)



        if(image == ""){
            image = "https://www.nytimes.com/images/2019/10/08/insider/08insider-barnardpapers/merlin_161922126_5f249167-3c97-40f1-b5e9-a47f42b115af-articleLarge.jpg"
        }



            val recyclerview = rootView.findViewById(R.id.rvTopStories) as RecyclerView
            recyclerview.layoutManager = LinearLayoutManager(activity)
            recyclerview.adapter = TopArticleAdapter(returnList)
            return rootView
   //     }
   //     return rootView
    }

}






