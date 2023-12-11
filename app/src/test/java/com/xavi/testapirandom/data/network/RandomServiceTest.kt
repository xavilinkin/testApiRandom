package com.xavi.testapirandom.data.network

import com.xavi.testapirandom.data.RandomRepository
import com.xavi.testapirandom.data.model.RandomModel
import com.xavi.testapirandom.data.model.TestData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class RandomServiceTest {
    @Test
    fun testGetRandomUsers() {
        // Given
        val randomRepository = RandomRepository()

        val apiField = RandomRepository::class.java.getDeclaredField("api")
        apiField.isAccessible = true

        val randomServiceMock = mockk<RandomService>()
        val mockResponse = TestData.createMockRandomModel()
        coEvery { randomServiceMock.getRandomUsers(any()) } returns Result.success(mockResponse)

        apiField.set(randomRepository, randomServiceMock)

        // When
        val result = runBlocking { randomRepository.getRandomUsers("1") }

        // Then
        assertEquals(Result.success(mockResponse), result)
    }

    @Test
    fun testGetRandomUsers_Failure() = runBlocking {
        // Given
        val randomRepository = RandomRepository()

        val apiField = RandomRepository::class.java.getDeclaredField("api")
        apiField.isAccessible = true

        val randomServiceMock = mockk<RandomService>()

        val errorResponseBody = ResponseBody.create(null, "Error message Test")
        val failureResponse = Response.error<RandomModel>(500, errorResponseBody)
        coEvery { randomServiceMock.getRandomUsers(any()) } returns Result.failure(Exception(failureResponse.toString()))

        apiField.set(randomRepository, randomServiceMock)

        // When
        val result = runBlocking { randomRepository.getRandomUsers("1") }

        // Then
        assertEquals(
            "java.lang.Exception: Response{protocol=http/1.1, code=500, message=Response.error(), url=http://localhost/}",
            result.exceptionOrNull().toString()
        )
    }
}
