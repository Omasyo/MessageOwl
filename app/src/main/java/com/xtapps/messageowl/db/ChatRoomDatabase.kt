package com.xtapps.messageowl.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.models.ChatRoomDao

@Database(entities = [ChatRoom::class], version = 1)
abstract class ChatRoomDatabase : RoomDatabase() {
    abstract fun chatRoomDao(): ChatRoomDao

    companion object {
        @Volatile
        private var INSTANCE: ChatRoomDatabase? = null

        fun getDatabase(context: Context): ChatRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChatRoomDatabase::class.java,
                    "room_database"
    ***REMOVED***.createFromAsset("db/test.db")
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***