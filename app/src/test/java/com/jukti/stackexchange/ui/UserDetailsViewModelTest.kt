package com.jukti.stackexchange.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jukti.stackexchange.data.model.Resource
import com.jukti.stackexchange.data.model.StackExchangeUserListApiResponse
import com.jukti.stackexchange.data.model.StackExchangeUserTagListApiResponse
import com.jukti.stackexchange.data.repository.StackExchangeRepository
import com.jukti.stackexchange.ui.details.UserDetailsViewModel
import com.jukti.stackexchange.utils.mock
import com.jukti.unrd.utils.TestUtil
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

@RunWith(JUnit4::class)
class UserDetailsViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val stackExchangeRepository = Mockito.mock(StackExchangeRepository::class.java)
    private val userDetailsViewModel = UserDetailsViewModel(stackExchangeRepository)

    @Test
    fun testCallRepo() {
        userDetailsViewModel.taglist.observeForever(mock())
        userDetailsViewModel.setUser(mock())
        Mockito.verify(stackExchangeRepository).getTopTagsByUserId(ArgumentMatchers.anyLong())
    }

    @Test
    fun sendResultToUI() {
        val responseLiveData = MutableLiveData<Resource<StackExchangeUserTagListApiResponse>>()
        Mockito.`when`(stackExchangeRepository.getTopTagsByUserId(ArgumentMatchers.anyLong())).thenReturn(responseLiveData)
        val observer = mock<Observer<Resource<StackExchangeUserTagListApiResponse>>>()
        userDetailsViewModel.taglist.observeForever(observer)
        userDetailsViewModel.setUser(mock())
        Mockito.verify(observer, Mockito.never()).onChanged(Mockito.any())
        val responseObj = TestUtil.createStackExchangeUserTagListResponse()
        val responseValue = Resource.success(responseObj)
        responseLiveData.value = responseValue
        Mockito.verify(observer).onChanged(responseValue)
    }
}