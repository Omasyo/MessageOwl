package com.xtapps.messageowl

import android.app.Application
import android.util.Log
import com.xtapps.messageowl.database.AppDatabase

class MessageOwlApplication: Application() {
    val appDatabase: AppDatabase by lazy {
        Log.d("Datassar", "lazying about")
        AppDatabase.getDatabase(this)
    }
}