package com.xtapps.messageowl

import android.app.Application
import com.xtapps.messageowl.db.ChatRoomDatabase

class MessageOwlApplication: Application() {
    val chatRoomDatabase: ChatRoomDatabase by lazy { ChatRoomDatabase.getDatabase(this) ***REMOVED***
***REMOVED***