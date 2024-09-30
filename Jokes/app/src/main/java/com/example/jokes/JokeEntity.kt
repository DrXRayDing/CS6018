package com.example.jokes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes_table")
data class JokeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val jokeText: String
)