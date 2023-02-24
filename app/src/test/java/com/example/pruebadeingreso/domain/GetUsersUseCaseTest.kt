package com.example.pruebadeingreso.domain

import com.example.pruebadeingreso.data.UserRepository
import com.example.pruebadeingreso.domain.model.User
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetUsersUseCaseTest{

    @RelaxedMockK
    private lateinit var userRepository: UserRepository

    lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getUsersUseCase = GetUsersUseCase(userRepository)
    }

    @Test
    fun `when Database Doesnt Return Anything Then Get Values From Api And Save It To Database`() = runBlocking {
        //Given
        coEvery { userRepository.getAllUsersFromDatabase() } returns emptyList()

        //When
        getUsersUseCase()

        //Then
        coVerify (exactly = 1) { userRepository.getAllUserFromApi() }
        coVerify (exactly = 1){ userRepository.insertUsers(any()) }
    }

    @Test
    fun `when Database return something then getData`() = runBlocking {
        //Given
        val myList = listOf(User(1,"TestName","TestPhone","TestEmail"))
        coEvery { userRepository.getAllUsersFromDatabase()} returns myList
        //When
        val response = getUsersUseCase()
        //Then
        coVerify (exactly = 0) { userRepository.getAllUserFromApi() }
        assert(myList == response)
    }
}