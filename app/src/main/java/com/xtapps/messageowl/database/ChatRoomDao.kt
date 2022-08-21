package com.xtapps.messageowl.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.xtapps.messageowl.models.ChatCardModel
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.models.ChatRoomUpdate
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatRoomDao {
    @Query("SELECT * FROM chat_rooms WHERE id in (SELECT DISTINCT room_id FROM messages)")
    fun getNonEmptyRooms(): Flow<List<ChatRoom>>

    @Query("SELECT * FROM chat_rooms ORDER BY id DESC")
    fun getAll(): Flow<List<ChatRoom>>

    @Query("SELECT * FROM chat_rooms WHERE id = :roomId")
    fun getRoom(roomId: String): Flow<ChatRoom>

    @Query("SELECT * FROM chat_rooms WHERE is_group = 0 AND participants like :userId")
    fun getPrivateRoom(userId: String): Flow<ChatRoom>

    @Query("SELECT * FROM " +
            "(SELECT room_id, chat_rooms.is_group as is_group," +
            "chat_rooms.name as room_name, sender_id," +
            "users.name as sender_name, content as recent, timestamp, unread " +
            "FROM messages " +
            "JOIN chat_rooms ON messages.room_id = chat_rooms.id " +
            "JOIN users ON sender_id = users.id " +
            "ORDER BY timestamp DESC) " +
            "GROUP BY room_id")
    fun getChatCards(): Flow<List<ChatCardModel>>

    @Insert(entity = ChatRoom::class)
    suspend fun insertRoom(chatRoom: ChatRoom)

    @Update(entity = ChatRoom::class)
    suspend fun updateRoom(room: ChatRoomUpdate): Int

//    @Update

//    @Query("SELECT participants FROM chat_rooms WHERE id = :roomId")
//    fun getParticipants(roomId: String): Flow<List<String>>
***REMOVED***