package com.xtapps.messageowl.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class MessageModel(
    @PrimaryKey val id: Int,
    val roomId: Int,
    val senderId: Int,
    val content: String,
    val timeStamp: String,
)
