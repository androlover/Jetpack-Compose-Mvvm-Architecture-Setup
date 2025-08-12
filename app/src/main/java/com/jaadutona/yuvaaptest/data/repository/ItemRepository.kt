package com.jaadutona.yuvaaptest.data.repository

class ItemRepository {
    fun getItems(): List<String> {
        return listOf("Apple", "Banana", "Orange")
    }
}