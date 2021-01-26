package com.marknorton.openclassroomsnytapp.ui

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
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.IMAGEBASEURL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class TechnologyFragment : Fragment() {
    private var returnList: ArrayList<Cell> = ArrayList()
    private var headline = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_top_stories, container, false)
        // Builder to get URL for Fragment
        val retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            // Do the GET request and get response
            val response = service.getTechnology()
            withContext(Dispatchers.Main) {
                val myList = getList(response)
                val recyclerview = rootView.findViewById(R.id.rvTopStories) as RecyclerView
                recyclerview.layoutManager = LinearLayoutManager(activity)
                recyclerview.adapter = ArticleAdapter(myList, context!!)
            }
        }
        return rootView
    }

    private fun getList(response: Response<TechnologyModel>): ArrayList<Cell> {
        if (response.isSuccessful) {
            // Process the response from API
            val body = response.body()?.results
            val results = body?.docs
            if (results != null) {
                for (h in 0 until results.count()) {
                    val section = results[h].section
                    var subSection = results[h].subsection
                    if (subSection != null) {
                        subSection = " > $subSection"
                    }
                    val url = results[h].url
                    var publishDate = results[h].publishDate
                    val dateData = publishDate?.split("T")
                    publishDate = dateData!![0]
                    val headlineData = results[h].headlineData
                    headline = headlineData?.headline!!
                    val db = Database(context!!)
                    db.addHeadline(headline)
                    val mediaData = results[h].mediaData
                    var mediaDataUrl = ""
                    // Process Image
                    if (mediaData != null) {
                        for (j in 0 until mediaData.count()) {
                            val mediaUrl = mediaData[j].mediaUrl
                            val mediaFormat = mediaData[j].mediaFormat
                            if (mediaFormat == "Standard Thumbnail") {
                                mediaDataUrl = "$IMAGEBASEURL/$mediaUrl"
                            }
                            if ((mediaDataUrl == "") && (mediaFormat == "thumbLarge")) {
                                mediaDataUrl = "$IMAGEBASEURL/$mediaUrl"
                            }
                        }
                    }
                    // Check Database to see if article has been read
                    val dbResult: Cursor = db.getHeadline(headline)
                    dbResult.moveToFirst()
                    var viewed = "0"
                    if (dbResult.count > 0) {
                        viewed = dbResult.getString(2)
                    }
                    val model = Cell(
                        section!!,
                        subSection!!,
                        headline,
                        url!!,
                        publishDate,
                        mediaDataUrl,
                        viewed
                    )
                    returnList.add(model)
                }
            }
        } else {
            Log.e("RETROFIT_ERROR", response.code().toString())
        }
        return returnList
    }
}
