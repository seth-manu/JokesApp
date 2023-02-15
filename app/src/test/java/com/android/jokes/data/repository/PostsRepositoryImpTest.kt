package com.android.jokes.data.repository

import com.android.jokes.domain.model.Joke
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test


class PostsRepositoryImpTest {

    @MockK
    lateinit var postsRepository: JokesRepositoryImp

    @Before
    fun setUp() {
        MockKAnnotations.init(this) //for initialization
    }

    @Test
    fun getPostsData() = runBlocking {
        val posts = mockk<List<Joke>>()
        every { runBlocking { postsRepository.getPosts() } } returns (posts)

        val result = postsRepository.getPosts()
        MatcherAssert.assertThat(
            "Received result [$result] & mocked [$posts] must be matches on each other!",
            result,
            CoreMatchers.`is`(posts)
        )
    }
}