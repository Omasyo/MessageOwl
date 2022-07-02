package com.xtapps.messageowl.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class MessageModel(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "room_id")
    val roomId: String,
    @ColumnInfo(name = "sender_id")
    val senderId: String,
//    @ColumnInfo(name = "content")
    val content: String,
//    val timeStamp: String,
)
