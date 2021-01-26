package com.marknorton.openclassroomsnytapp.ui

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marknorton.openclassroomsnytapp.*
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.BASEURL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MostPopularFragment : Fragment() {
    private var returnList: ArrayList<Cell> = ArrayList()
    private lateinit var headline: String

    @SuppressLint("DefaultLocale")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_most_popular, container, false)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            // Do the GET request and get response
            val response = service.getMostPopular()
            withContext(Dispatchers.Main) {

                val myList = getList(response)

                val recyclerview = rootView.findViewById(R.id.rvMostPopular) as RecyclerView
                recyclerview.layoutManager = LinearLayoutManager(activity)
                recyclerview.adapter = ArticleAdapter(myList, context!!)

            }
        }
        return rootView
    }

    @SuppressLint("DefaultLocale")
    private fun getList(response: Response<MostPopularModel>): ArrayList<Cell> {
        if (response.isSuccessful) {
            val items = response.body()?.results
            if (items != null) {
                for (i in 0 until items.count()) {
                    var section = items[i].section.toString()
                    val newSection = section.capitalize()
                    section = newSection
                    var subSection = items[i].subsection
                    val newSubSection = subSection?.capitalize()
                    subSection = newSubSection
                    if (subSection != "") {
                        subSection = "  > $subSection"
                    }
                    headline = items[i].title.toString()
                    val db = Database(context!!)
                    db.addHeadline(headline)
                    // Check Database to see if article has been read
                    val dbResult: Cursor = db.getHeadline(headline)
                    dbResult.moveToFirst()
                    var viewed = "0"
                    if (dbResult.count > 0) {
                        viewed = dbResult.getString(2)
                    }
                    val url = items[i].url
                    var publishDate = items[i].publishDate!!
                    val dateData = publishDate.split("T")
                    publishDate = dateData[0]
                    var mediaDataUrl = ""
                    val medias = items[i].media
                    if (medias != null) {
                        for (k in 0 until medias.count()) {
                            val mediaMeta = medias[k].mediaMeta
                            if (mediaMeta != null) {
                                for (m in 0 until mediaMeta.count()) {
                                    val mediaUrl =
                                        mediaMeta[m].mediaMetaUrl!!
                                    val mediaFormat =
                                        mediaMeta[m].mediaMetaFormat
                                    if (mediaFormat == "Standard Thumbnail") {
                                        mediaDataUrl = mediaUrl
                                    }
                                    if ((mediaDataUrl == "") && (mediaFormat == "thumbLarge")) {
                                        mediaDataUrl = mediaUrl
                                    }
                                }
                            }
                        }
                    }
                    val model = Cell(
                        section,
                        subSection,
                        headline,
                        url!!,
                        publishDate,
                        mediaDataUrl,
                        viewed
                    )
                    returnList.add(model)
                }
            } else {
                Log.d("Log", "Log - Needed Else")
            }
        } else {
            Log.e("RETROFIT_ERROR", response.code().toString())
        }
        return returnList
    }
}
