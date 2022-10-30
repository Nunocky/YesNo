package com.example.yesno

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import com.example.yesno.scene.main.MainFragment
import com.example.yesno.scene.main.MainViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@LargeTest
@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class MainFragmentOnLineTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testClickButton() = runTest {

        lateinit var fetchState: StateFlow<MainViewModel.FetchState>

        launchFragmentInHiltContainer<MainFragment> {
            fetchState = (this as MainFragment).viewModel.fetchState
        }

        onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click())

        val list = fetchState.take(2).toList()
        assertThat(list[0]).isInstanceOf(MainViewModel.FetchState.Fetching::class.java)
        assertThat(list[1]).isInstanceOf(MainViewModel.FetchState.Success::class.java)

        onView(ViewMatchers.withId(R.id.textView))
            .check(
                ViewAssertions.matches(
                    MultiTextMatcher.withTexts(
                        arrayOf("yes", "no", "maybe")
                    )
                )
            )
    }
}