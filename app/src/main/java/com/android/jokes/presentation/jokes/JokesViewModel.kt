package com.android.jokes.presentation.jokes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.jokes.domain.model.ApiError
import com.android.jokes.domain.model.Joke
import com.android.jokes.domain.usecase.GetJokesUseCase
import com.android.jokes.domain.usecase.base.UseCaseResponse
import kotlinx.coroutines.cancel


class JokesViewModel constructor(private val getPostsUseCase: GetJokesUseCase) : ViewModel() {

    val postsData = MutableLiveData<MutableList<String>>()
    val showProgressbar = MutableLiveData<Boolean>()
    val messageData = MutableLiveData<String>()
    private val jokes: MutableList<String> = mutableListOf()

    fun getJokes() {
        showProgressbar.postValue(true)
        getPostsUseCase.invoke(
            viewModelScope, null,
            object : UseCaseResponse<List<Joke>> {

                override fun onSuccess(result: String) {
                    Log.i(TAG, "result: $result")
                    if (jokes.size == 10) {
                        jokes.removeAt(0)
                        jokes.add(result)
                    } else {
                        jokes.add(result)
                    }
                    postsData.postValue(jokes)
                    showProgressbar.postValue(false)
                }

                override fun onError(apiError: ApiError?) {
                    messageData.postValue(apiError?.getErrorMessage())
                    showProgressbar.postValue((false))
                }
            },
        )
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

    companion object {
        private val TAG = JokesViewModel::class.java.name
    }

}