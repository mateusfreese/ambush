package com.ambush.ambushchallenge.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ambush.ambushchallenge.data.Repository
import com.ambush.ambushchallenge.data.model.AmbushRepository
import com.ambush.ambushchallenge.data.model.Language
import com.ambush.ambushchallenge.data.remote.ResultWrapper
import com.ambush.ambushchallenge.data.remote.state.State
import com.ambush.ambushchallenge.extensions.sortReposByIssues
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    private lateinit var sut: RepositoryViewModel

    @MockK
    private lateinit var repositoryMock: Repository

    @RelaxedMockK
    private lateinit var requestStateObserver: Observer<State>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        Dispatchers.setMain(testCoroutineDispatcher)

        sut = RepositoryViewModel(repositoryMock).apply {
            state.observeForever(requestStateObserver)
        }
    }

    @After
    fun tearDown() {
        sut.state.removeObserver(requestStateObserver)

        Dispatchers.resetMain()
        testCoroutineScope.cleanupTestCoroutines()
    }

    @Test
    fun `given request get ambush repos language list when on Success Should set state value`() {
        val expectedList = mockk<List<Language>>()
        val expectedResponse = ResultWrapper.Success(expectedList)

        coEvery { repositoryMock.getAmbushRepositoryLanguageList() } answers { expectedResponse }

        sut.getAmbushReposLanguageList()

        verifySequence {
            requestStateObserver.onChanged(State.Loading(true))
            requestStateObserver.onChanged(State.OnSuccessGetRepositoryLanguageList(expectedList))
            requestStateObserver.onChanged(State.Loading(false))
        }
    }

    @Test
    fun `given request get ambush repos language list when on Error Should set state value`() {
        val expectedResponse = ResultWrapper.Error("")

        coEvery { repositoryMock.getAmbushRepositoryLanguageList() } answers { expectedResponse }

        sut.getAmbushReposLanguageList()

        verifySequence {
            requestStateObserver.onChanged(State.Loading(true))
            requestStateObserver.onChanged(State.Error(""))
            requestStateObserver.onChanged(State.Loading(false))
        }
    }

    @Test
    fun `given request get ambush repos by language when on Success Should set state value`() {
        val expectedList = mockk<List<AmbushRepository>>(relaxed = true)
        val expectedResponse = ResultWrapper.Success(expectedList)

        coEvery { repositoryMock.getAmbushRepositoryByLanguage(any()) } answers { expectedResponse }

        sut.getAmbushRepositoriesByLanguage("")

        verifySequence {
            requestStateObserver.onChanged(State.Loading(true))
            requestStateObserver.onChanged(
                State.OnSuccessGetRepositoryFilteredByLanguage(
                    expectedList.sortReposByIssues()
                )
            )
            requestStateObserver.onChanged(State.Loading(false))
        }
    }

    @Test
    fun `given request get ambush repos by language when on Error Should set state value`() {
        val expectedResponse = ResultWrapper.Error("")

        coEvery { repositoryMock.getAmbushRepositoryByLanguage(any()) } answers { expectedResponse }

        sut.getAmbushRepositoriesByLanguage("")

        verifySequence {
            requestStateObserver.onChanged(State.Loading(true))
            requestStateObserver.onChanged(State.Error(""))
            requestStateObserver.onChanged(State.Loading(false))
        }
    }
}
