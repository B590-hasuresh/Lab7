package com.example.myapplication

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.lifecycle.testing.InstantTaskExecutorRule
import com.example.myapplication.R
import com.example.myapplication.ui.TicketDetailFragment
import com.example.myapplication.databinding.FragmentTicketDetailBinding
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TicketDetailFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() // Ensures LiveData runs synchronously

    @Test
    fun testEditTextAndCheckBox_updatesTicket() {
        // Launch the TicketDetailFragment
        val scenario = launchFragmentInContainer<TicketDetailFragment>()

        scenario.onFragment { fragment ->
            // Access views through binding
            val binding = fragment.view?.let { FragmentTicketDetailBinding.bind(it) }

            // Check if EditText is displayed
            onView(withId(R.id.ticket_title))
                .check(matches(isDisplayed()))
                .perform(typeText("Bug Fix"))

            // Check if CheckBox is displayed and click it
            onView(withId(R.id.ticket_solved))
                .check(matches(isDisplayed()))
                .perform(click())

            // Verify that the title is updated in the Ticket object
            assertEquals("Bug Fix", fragment.ticket.title)

            // Verify that the checkbox state updates the Ticket object
            assertTrue(fragment.ticket.isSolved)
        }
    }
}
