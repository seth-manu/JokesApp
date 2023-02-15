package com.android.jokes.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Joke(
    var mJoke: String
)