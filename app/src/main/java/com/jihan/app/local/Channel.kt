package com.jihan.app.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

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
