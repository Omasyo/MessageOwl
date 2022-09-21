package com.omaka.messageowl.models

import androidx.room.*
import java.util.*

@Entity(tableName = "messages",)
data class MessageModel(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "room_id", index = true)
    val roomId: String,
    @ColumnInfo(name = "sender_id", index = true)
    val senderId: String,

    val content: String,

    val timestamp: Date,
)

data class MessageWithSender(
    @Embedded
    val message: MessageModel,
    @Relation(
        parentColumn = "sender_id",
        entityColumn = "id"
    )
    val user: UserModel?
)
//data class MessageWithUser
