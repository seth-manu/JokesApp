package com.android.jokes.domain.usecase.base

import com.android.jokes.domain.model.ApiError

interface UseCaseResponse<Type> {

    fun onSuccess(result: String)

    fun onError(apiError: ApiError?)
}

