package com.jaadutona.yuvaaptest.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaadutona.yuvaaptest.data.repository.ApiRepository
import com.jaadutona.yuvaaptest.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ItemRepository,
    private val apiRepository: ApiRepository
) : ViewModel() {

    private val _items = MutableStateFlow<List<String>>(emptyList())
    val items: StateFlow<List<String>> = _items

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            try {
                // Local items (if needed)
                val localItems = repository.getItems()

                // API se items (example: user names)
                val apiUsers = apiRepository.fetchUsers() // List<User>
                val apiUserNames = apiUsers.map { it.name } // Assuming User has "name" field

                // Combine both lists
                _items.value = localItems + apiUserNames
            } catch (e: Exception) {
                e.printStackTrace()
                // You could emit error state here
            }
        }
    }
}
