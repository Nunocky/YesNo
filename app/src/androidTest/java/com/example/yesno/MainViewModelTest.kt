package com.example.yesno

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.yesno.data.YesNo
import com.example.yesno.repository.YesNoRepository
import com.example.yesno.repository.DefaultYesNoRepository
import com.example.yesno.scene.main.MainViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class MainViewModelTest {

    @get:Rule(order = 1)
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * オンラインでのテスト
     */
    @Test
    fun testOnline() {
        val viewModel = MainViewModel(DefaultYesNoRepository())

        runBlocking {
            viewModel.processFetch()
            Thread.sleep(1500)
        }

        assertThat(viewModel.fetchState.value).isInstanceOf(MainViewModel.FetchState.Success::class.java)

        val state = viewModel.fetchState.value as MainViewModel.FetchState.Success
        assertThat(state.yesno.answer).isAnyOf("yes", "no", "maybe")
    }

    @Test
    fun testYes() {
        // 必ず yesを返すモックオブジェクトを作る
        val mockRepository = mockk<YesNoRepository>()
        coEvery { mockRepository.fetch() } returns YesNo(
            answer = "yes",
            forced = false,
            image = "https://yesno.wtf/assets/yes/5-64c2804cc48057b94fd0b3eaf323d92c.gif"
        )

        val viewModel = MainViewModel(mockRepository)

        runBlocking {
            viewModel.processFetch()
            coVerify { mockRepository.fetch() }
        }
        // TODO viewModel.fetchState を observeする
        //      mokK, spekを学ぶ必要がありそう
        assert(viewModel.fetchState.value is MainViewModel.FetchState.Success)
        val state = viewModel.fetchState.value as MainViewModel.FetchState.Success

        assert(state.yesno.answer == "yes")
    }

    @Test
    fun testNo() {
        // 必ず noを返すモックオブジェクトを作る
        val mockRepository = mockk<YesNoRepository>()
        coEvery { mockRepository.fetch() } returns YesNo(
            answer = "no",
            forced = false,
            image = "https://yesno.wtf/assets/yes/5-64c2804cc48057b94fd0b3eaf323d92c.gif"
        )

        val viewModel = MainViewModel(mockRepository)

        runBlocking {
            viewModel.processFetch()
            coVerify { mockRepository.fetch() }
        }
        // TODO viewModel.fetchState を observeする
        //      mokK, spekを学ぶ必要がありそう
        assert(viewModel.fetchState.value is MainViewModel.FetchState.Success)
        val state = viewModel.fetchState.value as MainViewModel.FetchState.Success

        assert(state.yesno.answer == "no")
    }

    @Test
    fun testMaybe() {
        // 必ず maybeを返すモックオブジェクトを作る
        val mockRepository = mockk<YesNoRepository>()
        coEvery { mockRepository.fetch() } returns YesNo(
            answer = "maybe",
            forced = false,
            image = "https://yesno.wtf/assets/yes/5-64c2804cc48057b94fd0b3eaf323d92c.gif"
        )

        val viewModel = MainViewModel(mockRepository)

        runBlocking {
            viewModel.processFetch()
            coVerify { mockRepository.fetch() }
        }

        assert(viewModel.fetchState.value is MainViewModel.FetchState.Success)
        val state = viewModel.fetchState.value as MainViewModel.FetchState.Success

        assert(state.yesno.answer == "maybe")
    }

    @Test
    fun testNetworkError() {
        val mockRepository = mockk<YesNoRepository>()
        coEvery { mockRepository.fetch() } throws Exception("TEST")

        val viewModel = MainViewModel(mockRepository)

        runBlocking {
            viewModel.processFetch()
            coVerify { mockRepository.fetch() }
        }

        assert(viewModel.fetchState.value is MainViewModel.FetchState.Fail)
    }
}