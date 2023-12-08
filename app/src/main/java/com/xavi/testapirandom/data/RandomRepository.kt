package com.xavi.testapirandom.data

import com.xavi.testapirandom.data.model.RandomModel
import com.xavi.testapirandom.data.network.RandomService

class RandomRepository {
    private val api = RandomService()

    suspend fun getRandomUsers(page: String): RandomModel? {
        return api.getRandomUsers(page)
    }
}