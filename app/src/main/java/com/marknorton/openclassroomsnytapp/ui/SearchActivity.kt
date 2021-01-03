package com.marknorton.openclassroomsnytapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

//        Log.d("Log", "Log-CATEGORIES: $categories")
        doAsync {
            urls =
                (URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=news_desk:($categories)&q=Science&api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd").readText())
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
                    for (j in 0 until imageurl.length()) {
                        val d = imageurl.getJSONObject(j)
                        val subtype = d.getString("subtype")
// "master180"    blog225    "master315"    "thumbLarge"  "square320"

                        if (subtype == "thumbLarge") {
                            image = (d.getString("url"))
                            image = "https://www.nytimes.com/$image"
                        }
                    }
                    val headline = c.getJSONObject("headline")
                    theHeadline = (headline.getString("main"))
                    pubDate = (c.getString("pub_date"))
                    url = (c.getString("web_url"))

                    returnList.add(ArticleModel(section, image, theHeadline, pubDate, url))

                    image = ""
                }

                val recyclerview = findViewById<RecyclerView>(R.id.rvSearchResults)
                recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                recyclerview.adapter = ArticleAdapter(returnList, applicationContext)
            }
        }
        return
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
        }
        when(selectedOption){
            "about" ->{  val intent = Intent(this, About::class.java)
                //              intent.putExtra("uid", userUID)
                startActivity(intent)}
            "help" -> {
                val intent = Intent(this, Help::class.java)
                startActivity(intent)
            }
            "notification" -> {
                val intent = Intent(this, NotificationOptions::class.java)
                startActivity(intent)
            }
            "search" -> {
                showDialog()
            }
            "back" -> {
                this.onBackPressed()
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
        }

        builder.setPositiveButton("OK") { _, _ ->
            var categories = ""
            for (i in arrayCategories.indices) {
                val checked = arrayChecked[i]
                if (checked) {
                    counter++

                    if((arrayCategories[i]) == "Art") {
                        categories += "\"Art\" "
                    }
                    if((arrayCategories[i]) == "Business") {
                        categories += "\"Business\" "
                    }
                    if((arrayCategories[i]) == "Entrepreneurs") {
                        categories += "\"Entrepreneurs\" "
                    }
                    if((arrayCategories[i]) == "Politics") {
                        categories += "\"Politics\" "
                    }
                    if((arrayCategories[i]) == "Sports") {
                        categories += "\"Sports\" "
                    }
                    if((arrayCategories[i]) == "Travel") {
                        categories += "\"Travel\" "
                    }
                }
            }
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

