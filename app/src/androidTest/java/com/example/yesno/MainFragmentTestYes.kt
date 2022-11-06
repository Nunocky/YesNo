package com.example.yesno

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import com.example.yesno.di.YesNoDataSourceModule
import com.example.yesno.repository.YesNoDataSource
import com.example.yesno.scene.main.MainFragment
import com.example.yesno.scene.main.MainViewModel
import com.example.yesno.util.BaseRobot
import com.google.common.truth.Truth.assertThat
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
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
@UninstallModules(YesNoDataSourceModule::class)
@HiltAndroidTest
class MainFragmentTestYes {

    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class FakeYesDataSourceModule {
        @Binds
        abstract fun bindYesNoDataSource(dataSource: FakeYesDataSource): YesNoDataSource
    }

//    @BindValue
//    @JvmField
//    val dataSource: YesNoDataSource = FakeYesDataSource()

    @get:Rule(order = 0)
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

        Thread.sleep(1000)
        onView(withId(R.id.button)).perform(click())

        val list = fetchState.take(2).toList()
        assertThat(list[0]).isInstanceOf(MainViewModel.FetchState.Fetching::class.java)
        assertThat(list[1]).isInstanceOf(MainViewModel.FetchState.Success::class.java)
        Thread.sleep(100)
//        onView(withId(R.id.textView)).check(matches(withText("yes")))
        BaseRobot().assertOnView(withText("yes"), matches(isDisplayed()))
    }
}