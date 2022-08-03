package com.xtapps.messageowl.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "chat_rooms")
data class ChatRoom(
    @PrimaryKey
    val id: String,
    val name: String?,
    @ColumnInfo(name = "is_group")
    val isGroup: Boolean,
    val unread: Int,
    val participants: List<String>
)

data class ChatRoomUpdate(
    val id: String,
    val name: String?,
    val participants: List<String>
)

data class ChatCardModel(
    val room_id: String,
    val sender_id: String,
    @ColumnInfo(name = "is_group")
    val isGroup: Boolean,
    @ColumnInfo(name = "room_name")
    val roomName: String?,
    @ColumnInfo(name = "sender_name")
    val senderName: String,
    @ColumnInfo(name = "recent")
    val recentMessage: String,
    val timestamp: Date,
    val unread: Int?,
)