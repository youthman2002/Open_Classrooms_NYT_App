package com.marknorton.openclassroomsnytapp

import com.google.gson.annotations.SerializedName

/**
 *  Created by Mark Norton on 10/16/2019.
 *  Create Article Model
 *  Redone by Mark Norton on 01/08/2021.
 */


data class TopStoriesModel(
    var results: List<TSData>?
)
data class TSData(
    @SerializedName("section")
    val section: String?,
    @SerializedName("subsection")
    val subsection: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("published_date")
    val publishDate: String?,
    var multimedia: List<TSMedia>?
)
data class TSMedia(
    @SerializedName("url")
    val mediaUrl: String?,
    @SerializedName("format")
    val mediaFormat: String?
)




data class MostPopularModel(
    var results: List<MPData>?
)
data class MPData(
    @SerializedName("section")
    val section: String?,
    @SerializedName("subsection")
    val subsection: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("published_date")
    val publishDate: String?,
    var media: List<MPMedia>?
)
data class MPMedia(
    @SerializedName("type")
    val typeOfData: String?,
    @SerializedName("media-metadata")
    var mediaMeta: List<MPMediaMeta>?
)

data class MPMediaMeta(
    @SerializedName("url")
    val mediaMetaUrl: String?,
    @SerializedName("format")
    val mediaMetaFormat: String?
)


data class TechnologyModel(
    val status: String?,
    @SerializedName("response")
    var results: TechDocs
)

data class TechDocs(
    @SerializedName("docs")
    var docs: List<TechData>?
)

data class TechData(
    @SerializedName("news_desk")
    val section: String?,
    @SerializedName("section_name")
    val subsection: String?,
    @SerializedName("web_url")
    val url: String?,
    @SerializedName("pub_date")
    val publishDate: String?,
    @SerializedName("headline")
    var headlineData: TechHeadline?,
    @SerializedName("multimedia")
    var mediaData: List<TechMedia>?
)

data class TechHeadline(
    @SerializedName("main")
    val headline: String?
)

data class TechMedia(
    @SerializedName("url")
    val mediaUrl: String?,
    @SerializedName("subtype")
    val mediaFormat: String?
)


data class SearchModel(
    val status: String?,
    @SerializedName("response")
    var results: SearchDocs
)

data class SearchDocs(
    @SerializedName("docs")
    var docs: List<SearchData>?
)

data class SearchData(
    @SerializedName("news_desk")
    val section: String?,
    @SerializedName("section_name")
    val subsection: String?,
    @SerializedName("web_url")
    val url: String?,
    @SerializedName("pub_date")
    val publishDate: String?,
    @SerializedName("headline")
    var headlineData: SearchHeadline?,
    @SerializedName("multimedia")
    var mediaData: List<SearchMedia>?
)

data class SearchHeadline(
    @SerializedName("main")
    val headline: String?
)

data class SearchMedia(
    @SerializedName("url")
    val mediaUrl: String?,
    @SerializedName("subtype")
    val mediaFormat: String?
)