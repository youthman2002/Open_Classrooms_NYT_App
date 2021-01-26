
package com.marknorton.openclassroomsnytapp

/**
 *  Created by Mark Norton on 1/8/2021.
 *
 */
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.APIKEY
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.BEGINDATE
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.ENDDATE
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.NEWSDESK
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.Q
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    //   Do search for Top Stories (On Top Stories Tab)
    @GET("/svc/topstories/v2/science.json?api-key=$APIKEY")
    suspend fun getTopStories(): Response<TopStoriesModel>

    //   Do search for Technology Articles (On Technology Tab)
    @GET("/svc/search/v2/articlesearch.json?$Q=&api-key=$APIKEY&fq=news_desk:Technology")
    suspend fun getTechnology(): Response<TechnologyModel>

    //   Do search for Most Popular Articles (On Most Popular Tab)
    @GET("/svc/mostpopular/v2/viewed/30.json?api-key=$APIKEY")
    suspend fun getMostPopular(): Response<MostPopularModel>

    //  Do search for Search Page
    @GET("/svc/search/v2/articlesearch.json?$Q=&api-key=$APIKEY")
    suspend fun getSearch(
        @Query(NEWSDESK) categories: String,
        @Query(BEGINDATE) sdate: String,
        @Query(ENDDATE) edate: String,
        @Query(Q) searchString: String
    ): Response<TechnologyModel>
}
