package com.jaadutona.yuvaaptest.data.remote.model

import com.squareup.moshi.Json

data class Post(
    @Json(name = "userId") val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)