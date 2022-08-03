package com.xtapps.messageowl.models

import androidx.room.*
import java.util.*

@Entity(
    tableName = "messages",
    foreignKeys = [
        ForeignKey(
            entity = ChatRoom::class,
            parentColumns = ["id"],
            childColumns = ["room_id"],
        ),
        ForeignKey(
            entity = UserModel::class,
            parentColumns = ["id"],
            childColumns = ["sender_id"],
        )
    ]
)
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
    val user: UserModel
)
//data class MessageWithUser
