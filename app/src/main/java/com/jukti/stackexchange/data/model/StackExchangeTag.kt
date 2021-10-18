package com.jukti.stackexchange.data.model

import com.google.gson.annotations.SerializedName

data class StackExchangeTag(@SerializedName("user_id")val userID:Long,
                            @SerializedName("answer_count")val answerCount:Int,
                            @SerializedName("answer_score")val answerScore:Int,
                            @SerializedName("question_count")val questionCount:Int,
                            @SerializedName("question_score")val questionScore:Int,
                            @SerializedName("tag_name")val tagName:String
                            )
