package com.jukti.stackexchange.api;

import android.content.Context
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.jukti.stackexchange.data.model.Resource
import com.jukti.stackexchange.utilities.NetworkUtil

/**
 * Created by P|@b0n on 11/10/21.
 */
abstract class NetworkBoundResource<RequestType> (context: Context) {

        private val result = MediatorLiveData<Resource<RequestType>>()

        init {

            if (NetworkUtil.isConnected(context)) {
                setValue(Resource.loading(null))
                fetchFromNetwork()
            } else {
                setValue(Resource.noInternet())
            }
        }

        @MainThread
        private fun setValue(newValue: Resource<RequestType>) {
            if (result.value != newValue) {
                result.value = newValue
            }
        }

        private fun fetchFromNetwork() {
            val apiResponse = createCall()
            result.addSource(apiResponse) { response ->
                result.removeSource(apiResponse)

                when (response) {
                    is ApiSuccessResponse -> {
                        setValue(Resource.success(processResponse(response)))
                    }

                    is ApiErrorResponse -> {
                        onFetchFailed()
                        setValue(Resource.error(response.errorMessage, null))

                    }
                }
            }
        }

        protected fun onFetchFailed() {
        }

        fun asLiveData() = result as LiveData<Resource<RequestType>>

        @WorkerThread
        protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

        @MainThread
        protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}

