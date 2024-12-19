package com.jihan.app.local

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Immutable
@Serializable
@Entity("channel")
data class Channel(
    @PrimaryKey
    val id:Int,
    val name:String,
    val image:String,
    val url:String,
    val category:String
)
