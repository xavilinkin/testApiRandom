package com.xavi.testapirandom.data.network

import com.xavi.testapirandom.data.model.RandomModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface RandomApiClient {
    @GET
    suspend fun getSearchRandomUsers(@Url url: String): Response<RandomModel>
}