package com.xtapps.messageowl.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_room")
data class ChatRoom(
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo(name = "group")
    val isGroup: Boolean,
)