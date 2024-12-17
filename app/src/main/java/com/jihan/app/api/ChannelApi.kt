package com.jihan.app.api

import com.jihan.app.local.Channel
import com.jihan.app.util.Constants.CHANNEL_LIST_URL
import retrofit2.Response
import retrofit2.http.GET

interface ChannelApi {


    @GET(CHANNEL_LIST_URL)
    suspend fun getChannelList():List<Channel>



}