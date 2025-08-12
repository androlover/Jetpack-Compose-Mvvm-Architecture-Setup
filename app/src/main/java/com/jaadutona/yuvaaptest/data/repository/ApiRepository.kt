package com.jaadutona.yuvaaptest.data.repository



import com.jaadutona.yuvaaptest.data.remote.ApiService
import com.jaadutona.yuvaaptest.data.remote.model.User
import com.jaadutona.yuvaaptest.data.remote.model.Post

class ApiRepository(private val apiService: ApiService) {

    suspend fun fetchUsers(): List<User> {
        return apiService.getUsers()
    }

    suspend fun fetchPost(id: Int): Post {
        return apiService.getPost(id)
    }
}
