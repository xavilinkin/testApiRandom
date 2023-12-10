package com.xavi.testapirandom.data.network

import com.xavi.testapirandom.data.model.RandomModel
import com.xavi.testapirandom.data.model.TestData
import io.mockk.coEvery
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class RandomServiceTest {
    @Test
    fun testGetRandomUsers() = runBlocking {
        // Given
        val realRandomService = RandomService()
        val spyRandomService = spyk(realRandomService)
        val mockResponse = TestData.createMockRandomModel()

        coEvery { spyRandomService.getRandomUsers(any()) } returns Result.success(mockResponse)

        // When
        val result = spyRandomService.getRandomUsers("1")

        // Then
        assertEquals("John",
            result.getOrNull()?.results?.get(0)?.name?.first
        )
    }

    @Test
    fun testGetRandomUsers_Failure() = runBlocking {
        // Given
        val realRandomService = RandomService()
        val spyRandomService = spyk(realRandomService)

        val errorResponseBody = ResponseBody.create(null, "Error message Test")
        val failureResponse = Response.error<RandomModel>(500, errorResponseBody)
        coEvery { spyRandomService.getRandomUsers(any()) } returns Result.failure(
            Exception(
                failureResponse.toString()
            )
        )

        // When
        val result = spyRandomService.getRandomUsers("1")

        // Then
        assertEquals(
            "java.lang.Exception: Response{protocol=http/1.1, code=500, message=Response.error(), url=http://localhost/}",
            result.exceptionOrNull().toString()
        )
    }
}
