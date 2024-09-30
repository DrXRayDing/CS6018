package com.example.jokes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val chuckNorrisApi = ChuckNorrisApi.create()
    private val jokeDao = JokeDatabase.getDatabase(application).jokeDao()

    private val _jokes = MutableStateFlow<List<String>>(emptyList())
    val jokes = _jokes.asStateFlow()

    init {
        viewModelScope.launch (Dispatchers.IO) {
            // Collect the Flow from the database
            jokeDao.getAllJokes().collect { jokeEntities ->
                _jokes.value = jokeEntities.map { it.jokeText }
            }
        }
    }

    fun fetchJoke() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Fetch a joke from the API
                val response = chuckNorrisApi.getRandomJoke()

                // Insert the new joke into the database
                jokeDao.insertJoke(JokeEntity(jokeText = response.value))

                // No need to manually update _jokes; it will be updated by the init block
            } catch (e: Exception) {
                _jokes.value += "Failed to get joke: ${e.message}"
            }
        }
    }
}