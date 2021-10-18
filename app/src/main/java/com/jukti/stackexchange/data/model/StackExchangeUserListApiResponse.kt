package com.jukti.stackexchange.data.model

import com.google.gson.annotations.SerializedName

data class StackExchangeUserListApiResponse(@SerializedName("items")val users:List<StackExchangeUser>)
