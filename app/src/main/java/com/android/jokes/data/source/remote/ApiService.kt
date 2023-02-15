package com.android.jokes.data.source.remote

import retrofit2.http.GET

interface ApiService {

    @GET("/api")
    suspend fun getJokes(): String
}