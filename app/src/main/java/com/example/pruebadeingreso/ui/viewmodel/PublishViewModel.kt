package com.example.pruebadeingreso.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebadeingreso.data.model.PublishModel
import com.example.pruebadeingreso.domain.GetUsersPublishUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishViewModel @Inject constructor(
    private val getUsersPublishUseCase: GetUsersPublishUseCase
) : ViewModel() {

    val pubsModel = MutableLiveData<List<PublishModel>>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate(query: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getUsersPublishUseCase(query)
            if(!result.isNullOrEmpty()){
                pubsModel.postValue(result)
                isLoading.postValue(false)
            }
        }
    }

}