package com.xtapps.messageowl

import android.app.Application
import android.util.Log
import com.xtapps.messageowl.database.ChatRoomDatabase

class MessageOwlApplication: Application() {
    val chatRoomDatabase: ChatRoomDatabase by lazy {
        Log.d("Datassar", "lazying about")
        ChatRoomDatabase.getDatabase(this)
    }
}