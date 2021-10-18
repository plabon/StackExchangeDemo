package com.jukti.stackexchange.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jukti.stackexchange.data.model.Resource
import com.jukti.stackexchange.data.model.StackExchangeUserListApiResponse
import com.jukti.stackexchange.data.repository.StackExchangeRepository
import com.jukti.stackexchange.ui.search.SearchViewModel
import com.jukti.stackexchange.utils.mock
import com.jukti.unrd.utils.TestUtil
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class SearchViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val stackExchangeRepository = mock(StackExchangeRepository::class.java)
    private val searchViewModel = SearchViewModel(stackExchangeRepository)

    @Test
    fun testNull() {
        MatcherAssert.assertThat(searchViewModel.searchUserList, IsNull.notNullValue())
        verify(stackExchangeRepository, never()).getStackExchangeUsers(anyString())
        searchViewModel.setSearchQuery("coder")
        verify(stackExchangeRepository, never()).getStackExchangeUsers(anyString())
    }

    @Test
    fun testCallRepo() {
        searchViewModel.searchUserList.observeForever(mock())
        searchViewModel.setSearchQuery(anyString())
        verify(stackExchangeRepository).getStackExchangeUsers(anyString())
    }

    @Test
    fun sendResultToUI() {
        val responseLiveData = MutableLiveData<Resource<StackExchangeUserListApiResponse>>()
        `when`(stackExchangeRepository.getStackExchangeUsers(anyString())).thenReturn(responseLiveData)
        val observer = mock<Observer<Resource<StackExchangeUserListApiResponse>>>()
        searchViewModel.searchUserList.observeForever(observer)
        searchViewModel.setSearchQuery(anyString())
        verify(observer, never()).onChanged(any())
        val responseObj = TestUtil.createStackExchangeUserListResponse()
        val responseValue = Resource.success(responseObj)
        responseLiveData.value = responseValue
        verify(observer).onChanged(responseValue)
    }
}