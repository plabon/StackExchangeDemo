package com.jukti.stackexchange.data.repository

import android.content.Context
import android.provider.SyncStateContract
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.jukti.stackexchange.api.NetworkBoundResource
import com.jukti.stackexchange.api.StackExchangeApiService
import com.jukti.stackexchange.data.model.Resource
import com.jukti.stackexchange.data.model.StackExchangeUserListApiResponse
import com.jukti.stackexchange.data.model.StackExchangeUserTagListApiResponse
import com.jukti.unrd.utilities.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ShortIterator


/**
 * Created by P|@b0n on 17/10/21.
 */
@Singleton
class StackExchangeRepository @Inject constructor(@ApplicationContext private val context:Context,
                                                  private val apiService: StackExchangeApiService){

    fun getStackExchangeUsers(searchKey:String):LiveData<Resource<StackExchangeUserListApiResponse>>{
        return object : NetworkBoundResource<StackExchangeUserListApiResponse>(context) {
            override fun createCall() = apiService.getStackExchangeUsers(PAGE_NUMBER_VALUE,
                PAGE_SIZE_VALUE, ORDER_VALUE, SORT_BY_VALUE, searchKey,SITE_VALUE)
        }.asLiveData()
    }

    fun getTopTagsByUserId(userId:Long):LiveData<Resource<StackExchangeUserTagListApiResponse>>{
        return object : NetworkBoundResource<StackExchangeUserTagListApiResponse>(context) {
            override fun createCall() = apiService.getTopTagsByUserId(userId, PAGE_NUMBER_VALUE,
                PAGE_SIZE_TAG_VALUE,  SITE_VALUE)
        }.asLiveData()
    }


    companion object{
        private const val TAG = "StackExchangeRepository"
    }

}