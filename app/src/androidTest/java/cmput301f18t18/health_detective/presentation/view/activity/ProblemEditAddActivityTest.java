package cmput301f18t18.health_detective.presentation.view.activity;


import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cmput301f18t18.health_detective.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProblemEditAddActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> peaActivityTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Before
    public void LoginCredentials() {
        // Assuming walker2018 is a user, which it should be
        onView(withId(R.id.userIdLogin))
                .perform(replaceText("walker2018"),closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.addProbBtn)).perform(click());
    }

    // Testing to make sure view is displaying correctly
    @Test
    public void PEACorrectInfoDisplayedTest() {
        onView(withText("Problem Title")).check(matches(isDisplayed()));
        onView(withText("Problem Date")).check(matches(isDisplayed()));
        onView(withText("Description")).check(matches(isDisplayed()));
        onView(withId(R.id.addDateBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.saveBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.cancelBtn)).check(matches(isDisplayed()));
    }

    // Testing to make sure cancel button is working as intended
    @Test
    public void PEACancelButtonTest() {
        onView(withId(R.id.cancelBtn)).perform(click());
        intended(hasComponent(PatientProblemsActivity.class.getName()));
    }

    // Testing to make sure save button is working correctly and adding problem to list
    @Test
    public void PEASaveButtonTest() {
        onView(withHint("Problem Title"))
                .perform(replaceText("TestProblem"));
        onView(withId(R.id.saveBtn)).perform(click());
        intended(hasComponent(PatientProblemsActivity.class.getName()));
        onView(withText("TestProblem")).check(matches(isDisplayed()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onData(anything()).inAdapterView((withId(R.id.problemListView)))
                .atPosition(0)
                .onChildView(withId(R.id.deleteImg))
                .perform(click());
        onView(withText("CONFIRM")).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("TestProblem")).check(doesNotExist());
    }
}