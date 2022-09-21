package com.omaka.messageowl.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.omaka.messageowl.models.ChatRoom
import com.omaka.messageowl.models.ContactModel
import com.omaka.messageowl.models.MessageModel
import com.omaka.messageowl.models.UserModel
import com.omaka.messageowl.utils.Converters

@Database(
    entities = [
        ChatRoom::class,
        MessageModel::class,
        UserModel::class,
        ContactModel::class,
    ],
    version = 2
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatRoomDao(): ChatRoomDao
    abstract fun messageDao(): MessageDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "room_database"
    ***REMOVED***.build()
                INSTANCE = instance

                return instance
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***