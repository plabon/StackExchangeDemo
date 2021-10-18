package com.jukti.stackexchange.data.model

import com.google.gson.annotations.SerializedName

data class StackExchangeUserTagListApiResponse(@SerializedName("items")val toptags:List<StackExchangeTag>)
