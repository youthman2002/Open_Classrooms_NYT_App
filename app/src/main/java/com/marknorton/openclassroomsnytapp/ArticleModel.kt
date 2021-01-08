package com.marknorton.openclassroomsnytapp

import com.google.gson.annotations.SerializedName

/**
 *  Created by Mark Norton on 10/16/2019.
 *  Create Article Model
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


data class TechnologyModel(
    var results: List<TechData>?
)

data class TechData(
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
    var multimedia: List<TechMedia>?
)

data class TechMedia(
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
//    @SerializedName("media")
//    var medias: List<MPMedias>?,
    @SerializedName("type")
    val typeOfData: String?,
    @SerializedName("media-metadata")
    var mediaMeta: List<MPMediaMeta>?
)

data class MPMedias(
    @SerializedName("media-metadata")
    var mediaMeta: List<MPMediaMeta>?
)

data class MPMediaMeta(
    @SerializedName("url")
    val mediaMetaUrl: String?,
    @SerializedName("format")
    val mediaMetaFormat: String?
)