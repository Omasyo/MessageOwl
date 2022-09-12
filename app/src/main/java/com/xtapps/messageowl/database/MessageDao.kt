package com.xtapps.messageowl.database

import androidx.room.*
import com.xtapps.messageowl.models.MessageModel
import com.xtapps.messageowl.models.MessageWithSender
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Transaction
    @Query("SELECT * FROM messages WHERE room_id = :roomId ORDER BY timestamp")
    fun getMessages(roomId: String): Flow<List<MessageWithSender>>

    @Transaction
    @Query("SELECT * FROM messages WHERE room_id = :roomId ORDER BY timestamp DESC LIMIT 3")
    suspend fun getRecentMessages(roomId: String): List<MessageWithSender>

    @Query("SELECT DISTINCT sender_id FROM messages")
    fun getAllUsers(): Flow<List<String>>

//    @Query("SELECT * FROM messages WHERE room_id = :roomId ORDER BY timestamp DESC LIMIT 1")
//    fun getRecentMessage(roomId: String): Flow<MessageWithSender>

//    @Query("SELECT DISTINCT sender_id FROM messages WHERE room_id = :roomId")
//    fun getParticipants(roomId: String): Flow<List<String>>

    @Insert(entity = MessageModel::class, onConflict = OnConflictStrategy.IGNORE) //todo: the stuff shouldn't get old shit
    suspend fun insertMessage(message: MessageModel): Long
***REMOVED***