package com.android.jokes.domain.usecase

import com.android.jokes.domain.model.Joke
import com.android.jokes.domain.repository.JokesRepository
import com.android.jokes.domain.usecase.base.UseCase

class GetJokesUseCase constructor(
    private val postsRepository: JokesRepository
) : UseCase<List<Joke>, Any?>() {

    override suspend fun run(params: Any?): String {
        return postsRepository.getJokes()
    }

}