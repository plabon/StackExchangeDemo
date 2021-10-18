package com.jukti.stackexchange.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jukti.stackexchange.data.model.StackExchangeUser
import com.jukti.stackexchange.data.repository.StackExchangeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val repository: StackExchangeRepository
) : ViewModel() {

    var user = MutableLiveData<StackExchangeUser>()

    fun setUser(stackUser:StackExchangeUser){
        user.value = stackUser
    }

    val taglist = Transformations.switchMap(user) { searchText ->
        user.value?.let { repository.getTopTagsByUserId(it.userId) }
    }
}