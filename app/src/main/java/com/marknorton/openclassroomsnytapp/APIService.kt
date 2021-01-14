
package com.marknorton.openclassroomsnytapp

/**
 *  Created by Mark Norton on 1/8/2021.
 *
 */
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    //   Do search for Top Stories (On Top Stories Tab)
    @GET("/svc/topstories/v2/science.json?api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd")
    suspend fun getTopStories(): Response<TopStoriesModel>

    //   Do search for Technology Articles (On Technology Tab)
    @GET("/svc/search/v2/articlesearch.json?q=&api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd&" + "fq=news_desk:Technology")
    suspend fun getTechnology(): Response<TechnologyModel>

    //   Do search for Most Popular Articles (On Most Popular Tab)
    @GET("/svc/mostpopular/v2/viewed/30.json?api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd")
    suspend fun getMostPopular(): Response<MostPopularModel>

    //  Do search for Search Page
    @GET("/svc/search/v2/articlesearch.json?q=&api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd")
    suspend fun getSearch(
        @Query("news_desk:") categories: String,
        @Query("begin_date") sdate: String,
        @Query("end_date") edate: String,
        @Query("q") searchString: String
    ): Response<TechnologyModel>

    //   Do search for Technology Articles (On Technology Tab)
    @GET("/svc/search/v2/articlesearch.json?q=&api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd&" + "fq=news_desk:Technology")
    suspend fun getTechnologyTest(): Response<TechnologyModel>
}
