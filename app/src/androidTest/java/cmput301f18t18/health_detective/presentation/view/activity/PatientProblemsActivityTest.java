package cmput301f18t18.health_detective.presentation.view.activity;


import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.domain.repository.ProblemRepo;
import cmput301f18t18.health_detective.domain.repository.UserRepo;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.ProblemAddEditPresenter;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.SignUpPresenter;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.not;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class PatientProblemsActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> ppActivityTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Before
    public void LoginCredentials() {
        // Assuming walker2018 is a user, which it should be
        onView(withId(R.id.userIdLogin))
                .perform(replaceText("walker2018"),closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("OKAY")).perform(click());
        onView(withText("OKAY")).perform(click());
    }

    // Making sure correct activity is launched when add problem button is pressed
    @Test
    public void PPAddProblemTest() {
        onView(withId(R.id.addProbBtn)).perform(click());
        intended(hasComponent(ProblemEditAddActivity.class.getName()));
    }


    // Making sure the logout button is working as intended
    @Test
    public void PPLogoutButtonTest() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Logout")).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

    // Making user profile button launches correct activity
    @Test
    public void PPUserProfileButtonTest() {
        onView(withId(R.id.userId)).perform(click());
        intended(hasComponent(SignUpActivity.class.getName()));
    }

    // Making sure view is updated when problem added/deleted
    @Test
    public void PPProblemUpdateTest() {
        // Checking if added problem is correctly displayed
        onView(withId(R.id.addProbBtn)).perform(click());
        onView(withId(R.id.problemTitle))
                .perform(replaceText("TestProblem"));
        Date date = new Date();
        onView(withId(R.id.problemDate)).perform(replaceText(date.toString()));
        onView(withId(R.id.problemDesc))
                .perform(replaceText("Test Description"));
        onView(withId(R.id.saveBtn)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("TestProblem")).check(matches(isDisplayed()));
        onView(withText("RECORDS")).check(matches(isDisplayed()));
        onView(withText("Test Description")).check(matches(isDisplayed()));

        // Checking if problem was successfully deleted and no longer displayed
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
