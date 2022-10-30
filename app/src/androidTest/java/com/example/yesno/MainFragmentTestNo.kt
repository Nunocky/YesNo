package com.example.yesno

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import com.example.yesno.data.YesNo
import com.example.yesno.di.YesNoDataSourceModule
import com.example.yesno.repository.YesNoDataSource
import com.example.yesno.scene.main.MainFragment
import com.example.yesno.scene.main.MainViewModel
import com.google.common.truth.Truth.assertThat
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Singleton

class FakeNoDataSource @Inject constructor() : YesNoDataSource {
    override suspend fun fetch(force: String?): YesNo {

        runBlocking {
            Thread.sleep(300) // MEMO 300より小さいとうまくいかない。なぜ?
        }
        return YesNo(
            answer = "no",
            forced = false,
            image = "https://yesno.wtf/assets/no/12-dafd576be23d3768641340f76658ddfe.gif"
        )
    }
}

@LargeTest
@OptIn(ExperimentalCoroutinesApi::class)
@UninstallModules(YesNoDataSourceModule::class)
@HiltAndroidTest
class MainFragmentTestNo {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class FakeYesDataSourceModule {
        @Singleton
        @Binds
        abstract fun bindYesNoDataSource(dataSource: FakeNoDataSource): YesNoDataSource
    }

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testSuccess() = runTest {

        lateinit var fetchState: StateFlow<MainViewModel.FetchState>

        launchFragmentInHiltContainer<MainFragment> {
            fetchState = (this as MainFragment).viewModel.fetchState
        }

        onView(withId(R.id.button)).perform(click())

        val list = fetchState.take(2).toList()
        assertThat(list[0]).isInstanceOf(MainViewModel.FetchState.Fetching::class.java)
        assertThat(list[1]).isInstanceOf(MainViewModel.FetchState.Success::class.java)

        onView(withId(R.id.textView)).check(matches(withText("no")))
    }
}