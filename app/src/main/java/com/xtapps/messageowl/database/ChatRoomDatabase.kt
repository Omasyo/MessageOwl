package com.xtapps.messageowl.database

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
                    context,
                    ChatRoomDatabase::class.java,
                    "room_database"
                ).createFromAsset("database/test.db")
                    .build()
                INSTANCE = instance

                return instance
            }
        }
    }
}