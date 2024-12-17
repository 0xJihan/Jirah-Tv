package com.jihan.app.viewmodel

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jihan.app.local.Channel
import com.jihan.app.local.ChannelDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryDetailViewmodel(
    private val dao: ChannelDao
) : ViewModel() {
    private var _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private var _channelsList = MutableStateFlow<List<Channel>>(emptyList())
    val channelList = _channelsList.asStateFlow()


    fun getChannelList(category:String){
        _loading.value = true
        viewModelScope.launch {
       _channelsList.value = dao.getChannelsByCategory(category)
            _loading.emit(false)
        }
    }





}