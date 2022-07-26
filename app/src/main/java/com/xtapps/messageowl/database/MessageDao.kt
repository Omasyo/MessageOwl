package com.xtapps.messageowl.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.xtapps.messageowl.models.MessageModel
import com.xtapps.messageowl.models.MessageWithSender
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Transaction
    @Query("SELECT * FROM messages WHERE room_id = :roomId ORDER BY timestamp")
    fun getMessages(roomId: String): Flow<List<MessageWithSender>>

    @Query("SELECT DISTINCT sender_id FROM messages")
    fun getAllUsers(): Flow<List<String>>

    @Query("SELECT * FROM messages WHERE room_id = :roomId ORDER BY timestamp DESC LIMIT 1")
    fun getRecentMessage(roomId: String): Flow<MessageWithSender>

//    @Query("SELECT DISTINCT sender_id FROM messages WHERE room_id = :roomId")
//    fun getParticipants(roomId: String): Flow<List<String>>

    @Insert(entity = MessageModel::class)
    suspend fun insertMessage(message: MessageModel)
}