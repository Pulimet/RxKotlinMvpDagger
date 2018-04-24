package net.alexandroid.utils.rxkotlinmvpdagger

import android.content.pm.ActivityInfo
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import net.alexandroid.utils.rxkotlinmvpdagger.Utils.atPosition
import net.alexandroid.utils.rxkotlinmvpdagger.ui.main.MainActivity
import net.alexandroid.utils.rxkotlinmvpdagger.ui.main.MainAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SearchPhotosScreenTest {

    @Rule
    @JvmField
    val mActivity = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun testSearchFieldUpdatesTextView() {
        onView(withId(R.id.editText)).perform(typeText("a"))
        Thread.sleep(1000)
        onView(withId(R.id.textView)).check(matches(withText("a")))
    }

    @Test
    fun testSearchResults() {
        onView(withId(R.id.editText)).perform(typeText("dog"))
        Espresso.closeSoftKeyboard()
        Thread.sleep(2000)
        onView(withId(R.id.recyclerView)).check(matches(atPosition(0, hasDescendant(isDisplayed()))))

        onView(withId(R.id.editText)).perform(replaceText("lemur"))
        Thread.sleep(2000)
        onView(withId(R.id.recyclerView)).check(matches(atPosition(0, hasDescendant(isDisplayed()))))
    }

    @Test
    fun testScrollResults() {
        onView(withId(R.id.editText)).perform(typeText("tomato"))
        Espresso.closeSoftKeyboard()
        Thread.sleep(2000)
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions
                .scrollToPosition<MainAdapter.PhotoViewHolder>(19))
        Thread.sleep(500)
        onView(withId(R.id.recyclerView)).check(matches(atPosition(19, hasDescendant(isDisplayed()))))

    }

    @Test
    fun testClickOnItem() {
        onView(withId(R.id.editText)).perform(typeText("apple"))
        Espresso.closeSoftKeyboard()
        Thread.sleep(2000)
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions
                .actionOnItemAtPosition<MainAdapter.PhotoViewHolder>(2, click()))
        Thread.sleep(1000)
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
    }

    @Test
    fun testScreenRotation() {
        onView(withId(R.id.editText)).perform(typeText("cat"))
        mActivity.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        Espresso.closeSoftKeyboard()
        Thread.sleep(2000)
        onView(withId(R.id.textView)).check(matches(withText("cat")))
        onView(withId(R.id.recyclerView)).check(matches(atPosition(0, hasDescendant(isDisplayed()))))
    }
}
