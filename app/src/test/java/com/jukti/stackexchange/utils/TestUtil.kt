package com.jukti.unrd.utils

import com.jukti.stackexchange.data.model.*

object TestUtil {

    fun createStackExchangeUserListResponse() : StackExchangeUserListApiResponse{
        return StackExchangeUserListApiResponse(createTestUserListResult())
    }

    fun createStackExchangeUserTagListResponse() : StackExchangeUserTagListApiResponse{
        return StackExchangeUserTagListApiResponse(createTestTagListResult())
    }

    fun createTestUserListResult() : List<StackExchangeUser>{

        val testUserList=ArrayList<StackExchangeUser>()
        for (i in 1..20){
            testUserList.add(StackExchangeUser(i.toLong(),false,0L,0L,
                0,0,0,0,
                0,10,0L,"",i.toLong(),0,"London",
            "","","","", createStacExchangeBadgeTest()))
        }
        return testUserList
    }

    fun createStacExchangeBadgeTest() : StackExchangeBadgeCount{
        return StackExchangeBadgeCount(0,0,0)
    }

    fun createTestTagListResult() : List<StackExchangeTag>{

        val testtagList=ArrayList<StackExchangeTag>()
        for (i in 1..10){
            testtagList.add(StackExchangeTag(i.toLong(),0,0,0,
                0,"tag name"))
        }
        return testtagList
    }

}