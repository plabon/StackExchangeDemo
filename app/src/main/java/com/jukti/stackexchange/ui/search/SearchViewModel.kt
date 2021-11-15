package com.jukti.stackexchange.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.jukti.stackexchange.data.repository.StackExchangeRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: StackExchangeRepository
) : ViewModel() {

    private val searchQuery = MutableLiveData<String>()

    fun setSearchQuery(value:String){
        searchQuery.value = value
    }

    val searchUserList = Transformations.switchMap(searchQuery) { searchText ->
        repository.getStackExchangeUsers(searchText)
    }

    override fun onCleared() {
        super.onCleared()
        Log.e(TAG,"OnCleared")
    }

    companion object{
        private const val TAG = "SearchViewModel"
    }

}