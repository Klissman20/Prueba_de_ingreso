package com.example.pruebadeingreso.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebadeingreso.domain.GetUsersByNameUseCase
import com.example.pruebadeingreso.domain.GetUsersUseCase
import com.example.pruebadeingreso.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase:GetUsersUseCase,
    private val getUsersByNameUseCase:GetUsersByNameUseCase
) : ViewModel() {

    val userModel = MutableLiveData<List<User>>()
    val isLoading = MutableLiveData<Boolean>()
    val isEmpty = MutableLiveData<Boolean>()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getUsersUseCase()
            if(!result.isNullOrEmpty()){
                userModel.postValue(result)
                isLoading.postValue(false)
                isEmpty.postValue(false)
            }
        }
    }

    fun findByName(query: String) {
        viewModelScope.launch {
            val result = getUsersByNameUseCase(query)
            if(!result.isNullOrEmpty()){
                isEmpty.postValue(false)
                userModel.postValue(result)
            }else{
                isEmpty.postValue(true)
            }
        }
    }

}