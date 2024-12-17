package com.jihan.app.repository

import androidx.compose.runtime.MutableState
import com.jihan.app.api.ChannelApi
import com.jihan.app.local.Channel
import com.jihan.app.local.ChannelDao
import com.jihan.app.util.StateManager
import com.jihan.app.util.getCategories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChannelRepository(
    private val channelApi: ChannelApi,
    private val channelDao: ChannelDao
) {

    private var _channelList  = MutableStateFlow<StateManager<List<Channel>>>(StateManager.Loading)
    val channelList = _channelList.asStateFlow()

    private var _totalCategories = MutableStateFlow<List<String>>(emptyList())
    val totalCategories = _totalCategories.asStateFlow()


    suspend fun getChannelList() {

        _channelList.value = StateManager.Loading
        val response =  kotlin.runCatching { channelApi.getChannelList() }

        response.onSuccess {
            _channelList.value = StateManager.Success(it)
            _totalCategories.value = getCategories(it)
            channelDao.insertChannels(it)
        }.onFailure {
            _channelList.value = StateManager.Failed(it.cause?.localizedMessage?:"Unknown Error Occurred")
        }


    }





}
