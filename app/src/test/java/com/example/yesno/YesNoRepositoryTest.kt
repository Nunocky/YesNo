package com.example.yesno

import com.example.yesno.repository.DefaultYesNoDataSource
import com.example.yesno.repository.DefaultYesNoRepository
import com.example.yesno.repository.YesNoDataSource
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class YesNoRepositoryTest {

    @Test
    fun testForceYes() = runTest {
        val okHttpClient = OkHttpClient.Builder().build()
        val dataSource = DefaultYesNoDataSource(okHttpClient)
        val repository = DefaultYesNoRepository(dataSource)

        val result = repository.fetch(force = "yes")

        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun testNetworkError() = runTest {
        val dataSource = mockk<YesNoDataSource>()
        coEvery { dataSource.fetch(force = any()) } throws Throwable()
        val repository = DefaultYesNoRepository(dataSource)

        val result = repository.fetch(force = "yes")

        coVerify { dataSource.fetch(any()) }
        assertThat(result.isFailure).isTrue()
    }
}