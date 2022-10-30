package com.example.yesno

import com.example.yesno.repository.DefaultYesNoDataSource
import com.example.yesno.repository.YesNoDataSource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Test of YesNoRepository
 *
 * notice : should run online
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class YesNoDataSourceTest {

    private lateinit var dataSource: YesNoDataSource

    @Before
    fun setup() {
        val okHttpClient = OkHttpClient.Builder().build()
        dataSource = DefaultYesNoDataSource(okHttpClient)
    }

    @Test
    fun testCallOnlineApi() = runTest {
        val result = dataSource.fetch()

        assertThat(result.answer).isAnyOf("yes", "no", "maybe")
        assertThat(result.forced).isFalse()
        assertThat(result.image).isNotEmpty()
    }

    @Test
    fun testCallApiWithQueryYes() = runTest {
        val result = dataSource.fetch(force = "yes")

        assertThat(result.answer).isEqualTo("yes")
        assertThat(result.forced).isTrue()
        assertThat(result.image).isNotEmpty()
    }

    @Test
    fun testCallApiWithQueryNo() =
        runTest {
            val result = dataSource.fetch(force = "no")

            assertThat(result.answer).isEqualTo("no")
            assertThat(result.forced).isTrue()
            assertThat(result.image).isNotEmpty()
        }

    @Test
    fun testCallApiWithQueryMaybe() = runTest {
        val result = dataSource.fetch(force = "maybe")

        assertThat(result.answer).isEqualTo("maybe")
        assertThat(result.forced).isTrue()
        assertThat(result.image).isNotEmpty()
    }
}