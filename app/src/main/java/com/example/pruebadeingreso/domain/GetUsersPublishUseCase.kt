package com.example.pruebadeingreso.domain

import com.example.pruebadeingreso.data.UserRepository
import com.example.pruebadeingreso.data.model.PublishModel
import javax.inject.Inject

class GetUsersPublishUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(query: String): List<PublishModel> {
        return repository.getPublishFromApi(query)
    }
}