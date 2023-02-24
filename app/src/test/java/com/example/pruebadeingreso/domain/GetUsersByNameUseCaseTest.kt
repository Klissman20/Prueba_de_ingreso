package com.example.pruebadeingreso.domain

import com.example.pruebadeingreso.data.UserRepository
import com.example.pruebadeingreso.domain.model.User
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUsersByNameUseCaseTest{

    @RelaxedMockK
    private lateinit var userRepository: UserRepository

    lateinit var getUsersByNameUseCase: GetUsersByNameUseCase
    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getUsersByNameUseCase = GetUsersByNameUseCase(userRepository)
    }

    @Test
    fun `when Database Return Users Filtered by Query`() = runBlocking {
        //Given
        val myList = listOf(User(1,"TestName","TestPhone","TestEmail"))
        val query = "Te"
        coEvery { userRepository.getUsersByName(query)} returns myList
        //When
        val response = getUsersByNameUseCase(query)
        //Then
        assert(myList == response)
    }

    @Test
    fun `when Database Doesnt Return Any User filtered`() = runBlocking {
        //Given
        val query = "Te"
        coEvery { userRepository.getUsersByName(query) } returns emptyList()

        //When
        val response = getUsersByNameUseCase(query)

        //Then
        assert(response == emptyList<Any>())
    }
}