package com.example.pruebadeingreso.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pruebadeingreso.data.model.PublishModel
import com.example.pruebadeingreso.domain.GetUsersPublishUseCase
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
class PublishViewModelTest{
    @RelaxedMockK
    private lateinit var getUsersPublishUseCase: GetUsersPublishUseCase

    private lateinit var publishViewModel: PublishViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        publishViewModel = PublishViewModel(getUsersPublishUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is created at the first time, get all publishes`() = runTest{
        //Given
        val myList = listOf(PublishModel(1,1,"TestTittle","TestBody"), PublishModel(2,1,"TestTittle","TestBody"))
        val query = "/posts?userId=1"
        coEvery { getUsersPublishUseCase(query) } returns myList

        //When
        publishViewModel.onCreate(query)

        //Then
        assert(publishViewModel.pubsModel.value == myList)
    }

    @Test
    fun `when doesnt responde any publish by user`() = runTest{
        //Given
        val query = "/posts?userId=1"
        publishViewModel.pubsModel.value = emptyList()
        coEvery { getUsersPublishUseCase(query) } returns emptyList()

        //When
        publishViewModel.onCreate(query)

        //Then
        assert(publishViewModel.pubsModel.value == emptyList<Any>())
    }
}