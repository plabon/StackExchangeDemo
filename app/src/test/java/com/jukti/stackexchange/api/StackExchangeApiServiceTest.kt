package com.jukti.stackexchange.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jukti.stackexchange.data.model.StackExchangeUserListApiResponse
import com.jukti.stackexchange.utils.getOrAwaitValue
import com.jukti.unrd.utilities.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class StackExchangeApiServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: StackExchangeApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(StackExchangeApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }

    @Test
    fun getStackExchangeUsersResponse() {
        enqueueResponse("stackexchange_response.json")
        val response = (service.getStackExchangeUsers(PAGE_NUMBER_VALUE, PAGE_SIZE_VALUE,
            ORDER_VALUE, SORT_BY_VALUE,"coder", SITE_VALUE).getOrAwaitValue() as ApiSuccessResponse).body

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/2.3/users?page=1&pagesize=20&order=asc&sort=name&inname=coder&site=stackoverflow"))

        assertThat<StackExchangeUserListApiResponse>(response, notNullValue())
        assertThat(response.users.size, `is`(20))
        assertThat(response.users[0].userId, `is`(104891) )
    }
}