package com.xtapps.messageowl.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactModel(
    @PrimaryKey
    val id: String,
    val name: String
)

data class ContactCard(
    val id: String,
    val username: String,
    @ColumnInfo(name = "contact_name")
    val contactName: String,
    val image: String?,
)

data class ContactWithNumber(
    val name: String,
    val phoneNo: String,
)

