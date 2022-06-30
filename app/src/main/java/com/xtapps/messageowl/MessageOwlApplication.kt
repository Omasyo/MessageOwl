package com.xtapps.messageowl

import android.app.Application
import com.xtapps.messageowl.database.ChatRoomDatabase

class MessageOwlApplication: Application() {
    val chatRoomDatabase: ChatRoomDatabase by lazy { ChatRoomDatabase.getDatabase(this) }
}