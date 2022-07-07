package com.xtapps.messageowl.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "message")
data class MessageModel(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "room_id")
    val roomId: String,
    @ColumnInfo(name = "sender_id")
    val senderId: String,

    val content: String,

    val timestamp: Date,
)
