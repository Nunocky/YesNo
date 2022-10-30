package com.example.yesno

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.yesno.data.YesNo
import com.example.yesno.repository.DefaultYesNoDataSource
import com.example.yesno.repository.DefaultYesNoRepository
import com.example.yesno.repository.YesNoDataSource
import com.example.yesno.scene.main.MainViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

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
    fun testOnline() = runTest {
        val okHttpClient = OkHttpClient.Builder().build()
        val dataSource = DefaultYesNoDataSource(okHttpClient)
        val viewModel = MainViewModel(DefaultYesNoRepository(dataSource))

        viewModel.processFetch()

        val list = viewModel.fetchState.take(2).toList()
        assertThat(list[0]).isInstanceOf(MainViewModel.FetchState.Fetching::class.java)
        assertThat(list[1]).isInstanceOf(MainViewModel.FetchState.Success::class.java)

        val yesno = (list[1] as MainViewModel.FetchState.Success).yesno
        assertThat(yesno.answer).isAnyOf("yes", "no", "maybe")
        assertThat(yesno.forced).isFalse()
        assertThat(yesno.image).isNotEmpty()
    }

    @Test
    fun testYes() = runTest {
        val dataSource = mockk<YesNoDataSource>()

        coEvery { dataSource.fetch() } coAnswers {
            delay(30)

            YesNo(
                answer = "yes",
                forced = false,
                image = "https://yesno.wtf/assets/yes/5-64c2804cc48057b94fd0b3eaf323d92c.gif"
            )
        }

        val repository = DefaultYesNoRepository(dataSource)
        val viewModel = MainViewModel(repository)

        viewModel.processFetch()

        coVerify { dataSource.fetch() }

        val list = viewModel.fetchState.take(2).toList()
        assertThat(list[0]).isInstanceOf(MainViewModel.FetchState.Fetching::class.java)
        assertThat(list[1]).isInstanceOf(MainViewModel.FetchState.Success::class.java)
    }

    @Test
    fun testNo() = runTest {
        val dataSource = mockk<YesNoDataSource>()

        coEvery { dataSource.fetch() } coAnswers {
            delay(30)

            YesNo(
                answer = "no",
                forced = false,
                image = "https://yesno.wtf/assets/yes/5-64c2804cc48057b94fd0b3eaf323d92c.gif"
            )
        }
        val repository = DefaultYesNoRepository(dataSource)
        val viewModel = MainViewModel(repository)

        viewModel.processFetch()

        coVerify { dataSource.fetch() }

        val list = viewModel.fetchState.take(2).toList()
        assertThat(list[0]).isInstanceOf(MainViewModel.FetchState.Fetching::class.java)
        assertThat(list[1]).isInstanceOf(MainViewModel.FetchState.Success::class.java)
    }

    @Test
    fun testMaybe() = runTest {
        val dataSource = mockk<YesNoDataSource>()

        coEvery { dataSource.fetch() } coAnswers {
            delay(30)

            YesNo(
                answer = "maybe",
                forced = false,
                image = "https://yesno.wtf/assets/yes/5-64c2804cc48057b94fd0b3eaf323d92c.gif"
            )
        }
        val repository = DefaultYesNoRepository(dataSource)
        val viewModel = MainViewModel(repository)

        viewModel.processFetch()

        coVerify { dataSource.fetch() }

        val list = viewModel.fetchState.take(2).toList()
        assertThat(list[0]).isInstanceOf(MainViewModel.FetchState.Fetching::class.java)
        assertThat(list[1]).isInstanceOf(MainViewModel.FetchState.Success::class.java)
    }

    @Test
    fun testNetworkError() = runTest {
        val dataSource = mockk<YesNoDataSource>()

        coEvery { dataSource.fetch() } coAnswers {
            delay(30)
            throw IOException()
        }

        val repository = DefaultYesNoRepository(dataSource)
        val viewModel = MainViewModel(repository)

        viewModel.processFetch()

        coVerify { dataSource.fetch() }

        val list = viewModel.fetchState.take(2).toList()
        assertThat(list[0]).isInstanceOf(MainViewModel.FetchState.Fetching::class.java)
        assertThat(list[1]).isInstanceOf(MainViewModel.FetchState.Fail::class.java)
    }
}