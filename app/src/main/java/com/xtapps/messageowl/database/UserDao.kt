package com.xtapps.messageowl.database

import androidx.room.Dao
import androidx.room.Query
import com.xtapps.messageowl.models.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id in (:senderIds)")
    fun getUsers(senderIds: List<String>): Flow<List<UserModel>>
***REMOVED***