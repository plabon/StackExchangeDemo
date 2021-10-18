package com.jukti.stackexchange.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class StackExchangeUser(@SerializedName("account_id")val acountId:Long,
                             @SerializedName("is_employee")val isEmployee:Boolean,
                             @SerializedName("last_modified_date")val lastModifiedDate:Long,
                             @SerializedName("last_access_date") val lastAccessDate:Long,
                             @SerializedName("reputation_change_year")val reputationChangeYear:Int,
                             @SerializedName("reputation_change_quarter")val reputationChangeQuarter:Int,
                             @SerializedName("reputation_change_month") val reputationChangeMonth:Int,
                             @SerializedName("reputation_change_week")val reputationChangeWeek:Int,
                             @SerializedName("reputation_change_day")val reputationChangeDay:Int,
                             @SerializedName("reputation")val reputation:Int,
                             @SerializedName("creation_date")val creationDate:Long,
                             @SerializedName("user_type")val userType:String,
                             @SerializedName("user_id")val userId:Long,
                             @SerializedName("accept_rate")val acceptRate:Int,
                             @SerializedName("location")val location:String,
                             @SerializedName("website_url")val websiteUrl:String,
                             @SerializedName("link")val link:String,
                             @SerializedName("profile_image")val profileImage:String,
                             @SerializedName("display_name")val displayName:String,
                             @SerializedName("badge_counts")val badges:StackExchangeBadgeCount
):Parcelable{
    val creationDateFormatted: String
        get(){
            val currentDate = Date(creationDate*1000)
            val df: DateFormat = SimpleDateFormat("MMMM dd, yyyy")
            return df.format(currentDate)
        }
}




