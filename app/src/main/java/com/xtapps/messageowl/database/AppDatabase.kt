package com.xtapps.messageowl.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.models.AppDao
import com.xtapps.messageowl.models.MessageModel

@Database(entities = [ChatRoom::class, MessageModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
//    abstract fun appDao(): MessageModelDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "room_database"
    ***REMOVED***.createFromAsset("database/test.db")
                    .build()
                INSTANCE = instance

                return instance
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***