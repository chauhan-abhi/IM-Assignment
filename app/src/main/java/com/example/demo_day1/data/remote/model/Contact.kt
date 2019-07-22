package com.example.demo_day1.data.remote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.io.Serializable

@Entity(
    tableName = "contacts"
)
data class Contact(
    @Json(name = "personName")
    @ColumnInfo(name = "personName")
    val personName: String?,

    @Json(name = "personEmail")
    @ColumnInfo(name = "personEmail")
    val personEmail: String?,

    @Json(name = "contactNumber")
    @ColumnInfo(name = "contactNumber")
    @field: PrimaryKey
    val contactNumber: String,

    @Json(name = "profileImageUrl")
    @ColumnInfo(name = "profileImageUrl")
    val profileImageUrl: String? = "https://avatars1.githubusercontent.com/u/20797673?s=460&v=4"
) : Serializable