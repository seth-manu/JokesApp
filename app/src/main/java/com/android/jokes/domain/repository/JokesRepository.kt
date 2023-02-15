package com.android.jokes.domain.repository


interface JokesRepository {

    suspend fun getJokes(): String
}