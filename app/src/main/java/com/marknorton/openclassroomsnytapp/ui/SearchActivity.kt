package com.marknorton.openclassroomsnytapp.ui

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
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

class SearchActivity : AppCompatActivity() {
    private var urls = ""
    private var counter =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val categories = intent.getStringExtra("Categories")
//        Toast.makeText(this,"Categories:$categories", Toast.LENGTH_LONG).show()
//        val rootView = inflater.inflate(R.layout.fragment_top_stories, container, false)
        Log.d("Log", "Log-CATEGORIES: $categories")
        doAsync {
            urls =
                (URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=news_desk:($categories)&q=Science&api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd").readText())
            Log.d("Log", "Log-URLS: $urls")
            onComplete {
                val returnList = ArrayList<ArticleModel>()
//        Thread.sleep(2000)
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
//            var imageurl = c.getJSONObject("multimedia")
                    for (j in 0 until imageurl.length()) {
                        val d = imageurl.getJSONObject(j)
                        val subtype = d.getString("subtype")
                        //              Log.d("Log","Log-SUBTYPE:                    $subtype")
// "master180"    blog225    "master315"    "thumbLarge"  "square320"

                        if (subtype == "thumbLarge") {
//                    image.add(d.getString("url"))
                            image = (d.getString("url"))
//                    Log.d("Log","Log-IMAGE:                    $image")
                            //                   image = (d.getString("url"))
                            image = "https://www.nytimes.com/$image"

                        }


                    }
                    val headline = c.getJSONObject("headline")
                    theHeadline = (headline.getString("main"))
                    pubDate = (c.getString("pub_date"))
                    url = (c.getString("web_url"))

                    returnList.add(ArticleModel(section, image, theHeadline, pubDate, url))
                    Log.d(
                        "Log", "Log-INFO: $section, $image, $theHeadline, $pubDate, $url"
                    )
                    image = ""
                }

                val recyclerview = findViewById<RecyclerView>(R.id.rvSearchResults)
                recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                recyclerview.adapter = ArticleAdapter(returnList, applicationContext)
            }
        }
        return
        //     }
        //     return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var selectedOption = ""
        when (item.itemId) {
            R.id.menuAbout -> selectedOption = "about"
            R.id.menuHelp -> selectedOption = "help"
            R.id.menuNotification -> selectedOption = "notification"
            R.id.menuSearch -> selectedOption = "search"
//            R.id.exit -> selectedOption = "exit"
//            R.id.menuBack -> selectedOption = "back"
        }
        when(selectedOption){
            "about" ->{  val intent = Intent(this, About::class.java)
                //              intent.putExtra("uid", userUID)
                startActivity(intent)}
            "help"->{  val intent = Intent(this, Help::class.java)
                startActivity(intent)}
            "notification" ->{val intent = Intent(this, Notification::class.java)
                startActivity(intent)}
            "search" ->{
                showDialog()
            }
//                val intent = Intent(this, SearchActivity::class.java)
//                startActivity(intent)}
//            "exit" ->{finish()}
            "back" ->{this.onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
    private fun showDialog(){
        lateinit var dialog: AlertDialog
        val arrayCategories = arrayOf("Art","Business","Entrepreneurs","Politics","Sports","Travel")
        val arrayChecked = booleanArrayOf(true,false,false,false,false,false)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Category . . . ")
        builder.setMultiChoiceItems(arrayCategories, arrayChecked) { _, which, isChecked ->
            arrayChecked[which] = isChecked
//            Toast.makeText(this,"$category clicked.", Toast.LENGTH_SHORT).show()
        }

        builder.setPositiveButton("OK") { _, _ ->
            var categories = ""
            for (i in arrayCategories.indices) {
                val checked = arrayChecked[i]
                if (checked) {
                    counter++

                    if((arrayCategories[i]) == "Art") {
//   article search api      https://api.nytimes.com/svc/search/v2/articlesearch.json?q=election&api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd
//                text_view.text = "${text_view.text}  ${arrayColors[i]} \n"
//                        Toast.makeText(this,"Art clicked.", Toast.LENGTH_SHORT).show()
                        categories += "\"Art\" "
                    }
                    if((arrayCategories[i]) == "Business") {
//                        Toast.makeText(this,"Business clicked.", Toast.LENGTH_SHORT).show()
                        categories += "\"Business\" "
                    }
                    if((arrayCategories[i]) == "Entrepreneurs") {
//                        Toast.makeText(this,"Entrepreneurs clicked.", Toast.LENGTH_SHORT).show()
                        categories += "\"Entrepreneurs\" "
                    }
                    if((arrayCategories[i]) == "Politics") {
//                        Toast.makeText(this,"Politics clicked.", Toast.LENGTH_SHORT).show()
                        categories += "\"Politics\" "
                    }
                    if((arrayCategories[i]) == "Sports") {
//                        Toast.makeText(this,"Sports clicked.", Toast.LENGTH_SHORT).show()
                        categories += "\"Sports\" "
                    }
                    if((arrayCategories[i]) == "Travel") {
//                        Toast.makeText(this,"Travel clicked.", Toast.LENGTH_SHORT).show()
                        categories += "\"Travel\" "
                    }
                }
            }
//            Toast.makeText(this,"$counter clicked.", Toast.LENGTH_SHORT).show()
            if(counter < 1){
                Toast.makeText(this,"You MUST choose at least 1 Category.", Toast.LENGTH_SHORT).show()
                showDialog()
            }
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("Categories", categories)
            startActivity(intent)
        }
        dialog = builder.create()
        dialog.show()
        return
    }

}

