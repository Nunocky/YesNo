package com.example.yesno

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import com.squareup.spoon.SpoonRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@LargeTest
@HiltAndroidTest
class ScreenshotTest0 {
    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule(order = 3)
    var runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        READ_EXTERNAL_STORAGE,
        WRITE_EXTERNAL_STORAGE
    )

    @Rule(order = 4)
    @JvmField
    val spoon = SpoonRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun test0() {
        activityScenarioRule.scenario.onActivity {
            spoon.screenshot(it, "xxx")
        }

        onView(withText("CLICK!")).perform(click())

        Thread.sleep(10000)

        activityScenarioRule.scenario.onActivity {
            spoon.screenshot(it, "xxx1")
        }
    }
}