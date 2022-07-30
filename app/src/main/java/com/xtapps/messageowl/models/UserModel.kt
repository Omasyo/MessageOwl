package com.xtapps.messageowl.models

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserModel(
    @PrimaryKey
    val id: String,

    val name: String,
    @ColumnInfo(name = "phone")
    val phoneNo: String,
    @ColumnInfo(name = "pic")
    val profilePic: String?,
)