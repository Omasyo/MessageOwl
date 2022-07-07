package com.xtapps.messageowl.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.models.MessageModel
import com.xtapps.messageowl.utils.Converters

@Database(entities = [ChatRoom::class, MessageModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatRoomDao(): ChatRoomDao
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "room_database"
                ).createFromAsset("database/test.db")
                    .build()
                INSTANCE = instance

                return instance
            }
        }
    }
}