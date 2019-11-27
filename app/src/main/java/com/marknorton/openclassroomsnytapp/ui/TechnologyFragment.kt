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
import org.json.JSONObject
import java.net.URL
import java.util.*

class TechnologyFragment : Fragment() {
    var urls = ""
    var counter =0

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
                (URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=news_desk:(\"Technology\")&q=Science&api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd").readText())
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
            for (j in 0 until imageurl!!.length()) {
                val d = imageurl?.getJSONObject(j)
                val subtype = d.getString("subtype")

                if (subtype == "articleInline") {
                    image = (d.getString("url"))
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
        recyclerview.layoutManager = LinearLayoutManager(getActivity())
        recyclerview.adapter = ArticleAdapter(returnList, getContext()!!)
        return rootView
    }


    private fun showDialog(){
        lateinit var dialog:AlertDialog
        val arrayCategories = arrayOf("Art","Business","Entrepreneurs","Politics","Sports","Travel")
        val arrayChecked = booleanArrayOf(true,false,false,false,false,false)
        val builder = AlertDialog.Builder(getContext()!!)
        builder.setTitle("Choose Category . . . ")
        builder.setMultiChoiceItems(arrayCategories, arrayChecked, {dialog,which,isChecked->
            arrayChecked[which] = isChecked
            val category = arrayCategories[which]
            Toast.makeText(getContext(),"$category clicked.",Toast.LENGTH_SHORT).show()
        })

        builder.setPositiveButton("OK") { _, _ ->
            // Do something when click positive button
            for (i in 0 until arrayCategories.size) {
                val checked = arrayChecked[i]
                if (checked) {
                      counter++

                    if((arrayCategories[i]) == "Art") {
//   article search api      https://api.nytimes.com/svc/search/v2/articlesearch.json?q=election&api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd
//                text_view.text = "${text_view.text}  ${arrayColors[i]} \n"
                    }
                }
            }
            Toast.makeText(getContext(),"$counter clicked.",Toast.LENGTH_SHORT).show()
            if(counter < 1){
                Toast.makeText(getContext(),"You MUST choose at least 1 Category.",Toast.LENGTH_SHORT).show()
                showDialog()
            }
        }
        dialog = builder.create()
        dialog.show()
        return
    }


}

