package com.omaka.messageowl

import android.app.Application
import com.omaka.messageowl.database.AppDatabase

class MessageOwlApplication: Application() {
    val appDatabase: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
}