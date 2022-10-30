package com.example.yesno

import android.view.View
import android.widget.TextView
import androidx.test.espresso.matcher.BoundedMatcher

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