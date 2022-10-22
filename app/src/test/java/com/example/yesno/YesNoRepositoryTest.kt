package com.example.yesno

import com.example.yesno.repository.YesNoRepository
import com.example.yesno.repository.YesNoRepositoryImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Test of YesNoRepository
 *
 * notice : Run this test online.
 */
@RunWith(JUnit4::class)
class YesNoRepositoryTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testCallApi() {
        runTest {

            val repository: YesNoRepository = YesNoRepositoryImpl()

            // DDos attackになるのでやらないほうがいい
            // repeat(3) {
            val result = repository.fetch()

            assertThat(result.isSuccess).isTrue()
            assertThat(result.getOrNull()?.answer).isAnyOf("yes", "no", "maybe")
            assertThat(result.getOrNull()?.forced).isEqualTo(false)
            assertThat(result.getOrNull()?.image).isNotNull()
            // }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testCallApiWithQueryYes() {
        runTest {
            val repository: YesNoRepository = YesNoRepositoryImpl()
            val result = repository.fetch(force = "yes")

            assertThat(result.isSuccess).isTrue()
            assertThat(result.getOrNull()?.answer).isEqualTo("yes")
            assertThat(result.getOrNull()?.forced).isEqualTo(true)
            assertThat(result.getOrNull()?.image).isNotNull()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testCallApiWithQueryNo() {
        runTest {
            val repository: YesNoRepository = YesNoRepositoryImpl()
            val result = repository.fetch(force = "no")

            assertThat(result.isSuccess).isTrue()
            assertThat(result.getOrNull()?.answer).isEqualTo("no")
            assertThat(result.getOrNull()?.forced).isEqualTo(true)
            assertThat(result.getOrNull()?.image).isNotNull()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testCallApiWithQueryMaybe() {
        runTest {
            val repository: YesNoRepository = YesNoRepositoryImpl()
            val result = repository.fetch(force = "maybe")

            assertThat(result.isSuccess).isTrue()
            assertThat(result.getOrNull()?.answer).isEqualTo("maybe")
            assertThat(result.getOrNull()?.forced).isEqualTo(true)
            assertThat(result.getOrNull()?.image).isNotNull()
        }
    }
}