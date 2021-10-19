package com.jukti.stackexchange.api

import androidx.lifecycle.LiveData
import com.jukti.stackexchange.data.model.StackExchangeUserListApiResponse
import com.jukti.stackexchange.data.model.StackExchangeUserTagListApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StackExchangeApiService {

        @GET("/2.3/users")
        fun getStackExchangeUsers(@Query("page")pageNumber:Int,
                                       @Query("pagesize")pageSize:Int,
                                       @Query("order")order:String,
                                       @Query("sort")sort:String,
                                       @Query("inname")inName:String,
                                       @Query("site")site:String
        ) : LiveData<ApiResponse<StackExchangeUserListApiResponse>>

        @GET("2.3/users/{id}/top-tags")
        fun getTopTagsByUserId(@Path("id")id:Long,
                               @Query("page")pageNumber:Int,
                               @Query("pagesize")pageSize:Int,
                               @Query("site")site:String
        ) : LiveData<ApiResponse<StackExchangeUserTagListApiResponse>>
}