package com.example.yesno

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.yesno.MultiTextMatcher.Companion.withTexts
import com.example.yesno.scene.main.MainFragment
import com.example.yesno.scene.main.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.fail
import kotlinx.coroutines.flow.StateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test


// TODO 書きたいテスト
//  * yesを受信したとき → テキストに "yes"と表示されること
//  * noを受信したとき → テキストに "no"と表示されること
//  * maybeを受信したとき → テキストに "maybe"と表示されること
//  * エラーが起きたとき  → ダイアログが表示されること

@HiltAndroidTest
class MainFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testSuccess() {

        // TODO 気に入らない点 viewModelが privateでなくなった
        //      fetchStateの変化の監視方法がダサい
        lateinit var fetchState: StateFlow<MainViewModel.FetchState>

        launchFragmentInHiltContainer<MainFragment> {
            fetchState = (this as MainFragment).viewModel.fetchState
        }

        onView(withId(R.id.button)).perform(click())

        // TODO fetchStateが Successになるのを待つ (タイムアウトあり)
        var timeout = 10_000
        while (0 < timeout) {
            Thread.sleep(100)

            if (fetchState.value is MainViewModel.FetchState.Success) {
                break
            }
            timeout -= 100
        }

        if (timeout == 0) {
            fail()
        }

        // TODO 「yes no maybeのいずれかにマッチ」を記述したい
        onView(withId(R.id.textView)).check(matches(withTexts(arrayOf("yes", "no", "maybe"))))
    }

    @Test
    fun testYes() {
        fail()
    }

    @Test
    fun testNo() {
        fail()
    }

    @Test
    fun testMaybe() {
        fail()
    }

    @Test
    fun testOffline() {
        fail()
    }
}

internal class MultiTextMatcher private constructor(private val texts: Array<String>) :
    BoundedMatcher<View?, TextView>(TextView::class.java) {
    override fun matchesSafely(item: TextView): Boolean {
        for (text in texts) {
            if (item.text.toString() == text) {
                return true
            }
        }
        return false
    }

    override fun describeTo(description: org.hamcrest.Description?) {
        description?.appendText("with texts:")?.appendValue(texts.toString())
    }

    companion object {
        fun withTexts(texts: Array<String>): MultiTextMatcher {
            return MultiTextMatcher(texts)
        }
    }
}