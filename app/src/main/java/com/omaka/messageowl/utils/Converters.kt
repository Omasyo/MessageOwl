package com.omaka.messageowl.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return Gson().fromJson<List<String>>(value, List::class.java)
    }

    @TypeConverter
    fun fromArrayList(list: List<String>?): String? {
        return Gson().toJson(list)
    }
}