package com.example.myapplication
import androidx.fragment.app.testing.launchFragmentInContainer
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.arch.core.executor.testing.InstantTaskExecutorRule


@RunWith(AndroidJUnit4::class)
class TicketDetailFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testCheckBoxAndEditText_updatesTicket() {
        // Launch the fragment with correct theme
        val scenario = launchFragmentInContainer<TicketDetailFragment>(
            Bundle(), R.style.Theme_MyApplication
        )

        scenario.onFragment { fragment ->
            val checkBox = fragment.view?.findViewById<CheckBox>(R.id.ticket_solved)
            val editText = fragment.view?.findViewById<EditText>(R.id.ticket_title)

            // Ensure views are not null
            assertNotNull(checkBox)
            assertNotNull(editText)
        }

        // Perform actions using Espresso
        onView(withId(R.id.ticket_solved)).perform(click()) // Click CheckBox
        onView(withId(R.id.ticket_title)).perform(typeText("Updated Ticket")) // Type text in EditText

        // Verify UI changes
        onView(withId(R.id.ticket_solved)).check(matches(isChecked()))
        onView(withId(R.id.ticket_title)).check(matches(withText("Updated Ticket")))
    }

}
