package com.jaadutona.yuvaaptest.data.remote

import com.jaadutona.yuvaaptest.data.remote.model.Post
import com.jaadutona.yuvaaptest.data.remote.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Post
}
