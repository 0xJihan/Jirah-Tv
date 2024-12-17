package com.jihan.app.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Channel::class], version = 1, exportSchema = false)
abstract class ChannelDatabase : RoomDatabase() {

    abstract fun getDao(): ChannelDao

    companion object {
        @Volatile
        private var instance: ChannelDatabase? = null

        fun getDatabase(context: Context): ChannelDatabase {

            synchronized(this) {


                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ChannelDatabase::class.java,
                        "channels"
                    ).build()
                }
            }
            return instance!!
        }


    }


}