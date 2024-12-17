package com.jihan.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jihan.app.repository.ChannelRepository
import kotlinx.coroutines.launch

class ChannelViewmodel(
    private val channelRepository: ChannelRepository,
) : ViewModel() {


    val channelList get() =  channelRepository.channelList
    val totalCategories get() = channelRepository.totalCategories




    init {
        getChannelList()
    }


    private fun getChannelList(){
        viewModelScope.launch {
            channelRepository.getChannelList()
        }
    }





}