package com.jukti.stackexchange.ui.details

import androidx.lifecycle.LiveData
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
    private val _user = MutableLiveData<StackExchangeUser>()
    var user:LiveData<StackExchangeUser> = _user;

    fun setUser(stackUser:StackExchangeUser){
        _user.value = stackUser
    }

    val taglist = Transformations.switchMap(user) { searchText ->
        user.value?.let { repository.getTopTagsByUserId(it.userId) }
    }
}