package com.marknorton.openclassroomsnytapp

/**
 *  Created by Mark Norton on 1/8/2021.
 *
 */
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    //  https://api.nytimes.com/svc/topstories/v2/science.json?api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd
    @GET("/svc/topstories/v2/science.json?api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd")
    suspend fun getTopStories(): Response<TopStoriesModel>

    //  https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=news_desk:(\"Technology\")&q=&api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd
    @GET("/svc/search/v2/articlesearch.json?fq=news_desk:(\\\"Technology\\\")&q=&api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd")
    suspend fun getTechnology(): Response<TechnologyModel>

    //   https://api.nytimes.com/svc/mostpopular/v2/viewed/30.json?api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd
    @GET("/svc/mostpopular/v2/viewed/30.json?api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd")
    suspend fun getMostPopular(): Response<MostPopularModel>
}