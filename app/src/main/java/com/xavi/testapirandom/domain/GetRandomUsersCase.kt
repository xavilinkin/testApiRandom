package com.xavi.testapirandom.domain

import com.xavi.testapirandom.data.RandomRepository
import com.xavi.testapirandom.data.model.RandomModel

class GetRandomUsersCase {
    private val repository = RandomRepository()

    suspend operator fun invoke(page: String): Result<RandomModel?> {
        return repository.getRandomUsers(page)
    }
}