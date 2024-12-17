package com.jihan.app.util

sealed class StateManager<out T>{
    data object Loading : StateManager<Nothing>()
    data class Success<out T> (val data:T) : StateManager<T>()
    data class Failed(val message:String) : StateManager<Nothing>()

}