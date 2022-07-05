package com.xtapps.messageowl.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class MessageModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "room_id")
    val roomId: Long,
    @ColumnInfo(name = "sender_id")
    val senderId: Long,
//    @ColumnInfo(name = "content")
    val content: String,
//    val timeStamp: String,
)
