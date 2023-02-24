package com.example.pruebadeingreso.domain

import com.example.pruebadeingreso.data.UserRepository
import com.example.pruebadeingreso.domain.model.User
import javax.inject.Inject

class GetUsersByNameUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(query: String): List<User> {
        return repository.getUsersByName(query)
    }
}