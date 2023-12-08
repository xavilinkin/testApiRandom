package com.xavi.testapirandom.data.network

import com.xavi.testapirandom.core.RetrofitHelper
import com.xavi.testapirandom.data.model.RandomModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RandomService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getRandomUsers(page: String): RandomModel? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(RandomApiClient::class.java).getSearchRandomUsers(
                "?page=$page&results=10&seed=abc"
            )
            if (response.isSuccessful) {
                response.body()
            } else {
                // TODO David esto se modificará en el futuro
                null
            }
        }
    }
}