package com.example.pruebadeingreso.domain

import com.example.pruebadeingreso.data.UserRepository
import com.example.pruebadeingreso.data.model.PublishModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUsersPublishUseCaseTest{

    @RelaxedMockK
    private lateinit var userRepository: UserRepository

    lateinit var getUsersPublishUseCase: GetUsersPublishUseCase
    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getUsersPublishUseCase = GetUsersPublishUseCase(userRepository)
    }

    @Test
    fun `when Database Return All Publishes by User`() = runBlocking {
        //Given
        val myList = listOf(PublishModel(1,1,"TestTittle","TestBody"))
        val query = "/posts?userId=1"
        coEvery { userRepository.getPublishFromApi(query)} returns myList
        //When
        val response = getUsersPublishUseCase(query)
        //Then
        assert(myList == response)
    }
}