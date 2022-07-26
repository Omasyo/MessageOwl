package com.xtapps.messageowl.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_rooms")
data class ChatRoom(
    @PrimaryKey
    val id: String,
    val name: String?,
    @ColumnInfo(name = "group")
    val isGroup: Boolean,

    val participants: List<String>
)

data class ChatWithRecent(
    val chatRoom: ChatRoom,
    val recentMessage: MessageWithSender
)