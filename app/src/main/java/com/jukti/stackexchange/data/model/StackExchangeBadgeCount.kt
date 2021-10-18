package com.jukti.stackexchange.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StackExchangeBadgeCount(@SerializedName("bronze")val bronze:Int,
                                   @SerializedName("silver")val silver:Int,
                                   @SerializedName("gold")val gold:Int
                                   ):Parcelable
