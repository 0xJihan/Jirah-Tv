package com.jihan.app.util

import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination {
    @Serializable
    data object Home:Destination


    @Serializable
    data class CategoryDetail(val category:String):Destination


    @Serializable
    data class StreamPlayer(val channelJson: String):Destination

}