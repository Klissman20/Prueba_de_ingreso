package com.example.pruebadeingreso.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pruebadeingreso.domain.GetUsersByNameUseCase
import com.example.pruebadeingreso.domain.GetUsersUseCase
import com.example.pruebadeingreso.domain.model.User
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserViewModelTest{
    @RelaxedMockK
    private lateinit var getUsersUseCase: GetUsersUseCase

    @RelaxedMockK
    private lateinit var getUsersByNameUseCase: GetUsersByNameUseCase

    private lateinit var userViewModel: UserViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        userViewModel = UserViewModel(getUsersUseCase, getUsersByNameUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is created at the first time, get all users`() = runTest{
        //Given
        val myList = listOf(User(1,"TestName","TestPhone","TestEmail"), User(2,"TestName","TestPhone","TestEmail"))
        coEvery { getUsersUseCase() } returns myList

        //When
        userViewModel.onCreate()

        //Then
        assert(userViewModel.userModel.value == myList)
    }

    @Test
    fun `when findbyname function return users filtered`() = runTest{
        //Given
        val user = listOf(User(1,"TestName","TestPhone","TestEmail"))
        val query = "/posts?userId=1"
        userViewModel.userModel.value = user
        coEvery { getUsersByNameUseCase(query) } returns user

        //When
        userViewModel.findByName(query)

        //Then
        assert(userViewModel.userModel.value == user)
    }

    @Test
    fun `when findbyname function doesnt return any users`() = runTest{
        //Given
        val query = "/posts?userId=1"
        userViewModel.userModel.value = emptyList()
        coEvery { getUsersByNameUseCase(query) } returns emptyList()

        //When
        userViewModel.findByName(query)

        //Then
        assert(userViewModel.userModel.value == emptyList<Any>())
    }

}