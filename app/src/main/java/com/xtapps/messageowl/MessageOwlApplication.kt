package com.xtapps.messageowl

import android.app.Application
import android.util.Log
import com.xtapps.messageowl.database.AppDatabase

class MessageOwlApplication: Application() {
    val appDatabase: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    ***REMOVED***
***REMOVED***