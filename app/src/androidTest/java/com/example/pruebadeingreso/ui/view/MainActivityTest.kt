package com.example.pruebadeingreso.ui.view


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.pruebadeingreso.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest2() {
        val recyclerView = onView(
            allOf(
                withId(R.id.recycler_view_fragment),
                childAtPosition(
                    withId(R.id.layout),
                    4
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(longClick())

        val appCompatImageButton2 = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())

        pressBack()

        val materialButton = onView(
            allOf(
                withId(R.id.pub_btn), withText("Ver publicaciones"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recycler_view_fragment),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val appCompatImageButton3 = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton3.perform(click())

        pressBack()

        val searchAutoComplete = onView(
            allOf(
                withId(com.google.android.material.R.id.search_src_text),
                childAtPosition(
                    allOf(
                        withId(com.google.android.material.R.id.search_plate),
                        childAtPosition(
                            withId(com.google.android.material.R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete.perform(replaceText("lk"), closeSoftKeyboard())

        val appCompatImageView = onView(
            allOf(
                withId(com.google.android.material.R.id.search_close_btn),
                withContentDescription("Clear query"),
                childAtPosition(
                    allOf(
                        withId(com.google.android.material.R.id.search_plate),
                        childAtPosition(
                            withId(com.google.android.material.R.id.search_edit_frame),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val searchAutoComplete2 = onView(
            allOf(
                withId(com.google.android.material.R.id.search_src_text),
                childAtPosition(
                    allOf(
                        withId(com.google.android.material.R.id.search_plate),
                        childAtPosition(
                            withId(com.google.android.material.R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete2.perform(replaceText("C"), closeSoftKeyboard())

        val searchAutoComplete3 = onView(
            allOf(
                withId(com.google.android.material.R.id.search_src_text), withText("C"),
                childAtPosition(
                    allOf(
                        withId(com.google.android.material.R.id.search_plate),
                        childAtPosition(
                            withId(com.google.android.material.R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete3.perform(pressImeActionButton())

        val recyclerView2 = onView(
            allOf(
                withId(R.id.recycler_view_fragment),
                childAtPosition(
                    withId(R.id.layout),
                    4
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val appCompatImageButton4 = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton4.perform(click())

        pressBack()

        val appCompatImageView2 = onView(
            allOf(
                withId(com.google.android.material.R.id.search_close_btn),
                withContentDescription("Clear query"),
                childAtPosition(
                    allOf(
                        withId(com.google.android.material.R.id.search_plate),
                        childAtPosition(
                            withId(com.google.android.material.R.id.search_edit_frame),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageView2.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
