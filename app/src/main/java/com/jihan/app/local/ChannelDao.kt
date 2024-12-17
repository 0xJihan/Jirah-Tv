package com.jihan.app.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ChannelDao {


     @Upsert
     suspend fun insertChannels(channels:List<Channel>)


     @Query("select * from channel")
     suspend fun getChannels() : List<Channel>

     @Query("select * from channel where category = :category")
     suspend fun getChannelsByCategory(category:String):List<Channel>

}