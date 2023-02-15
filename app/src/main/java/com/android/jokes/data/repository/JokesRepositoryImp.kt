package com.android.jokes.data.repository

import com.android.jokes.data.source.remote.ApiService
import com.android.jokes.domain.repository.JokesRepository

class JokesRepositoryImp(private val apiService: ApiService) : JokesRepository {

    override suspend fun getJokes(): String {
        return apiService.getJokes()
    }
}