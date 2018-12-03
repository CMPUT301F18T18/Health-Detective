package cmput301f18t18.health_detective.presentation.view.activity;


import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import cmput301f18t18.health_detective.R;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
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
public class PatientRecordsActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> prActivityTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Before
    public void LoginAndCreateTestProblem() {
        // Assuming walker2018 is a user, which it should be
        onView(withId(R.id.userIdLogin))
                .perform(replaceText("walker2018"),closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("OKAY")).perform(click());
        onView(withText("OKAY")).perform(click());
        onView(withId(R.id.addProbBtn)).perform(click());
        onView(withId(R.id.problemTitle))
                .perform(replaceText("RecordTest problem"));
        Date date = new Date();
        onView(withId(R.id.problemDate)).perform(replaceText(date.toString()));
        onView(withId(R.id.problemDesc))
                .perform(replaceText("RecordTest Description"));
        onView(withId(R.id.saveBtn)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onData(anything()).inAdapterView((withId(R.id.problemListView)))
                .atPosition(0)
                .onChildView(withId(R.id.recordsBut))
                .perform(click());
        onView(withText("OKAY")).perform(click());
        onView(withText("OKAY")).perform(click());
    }

    @After
    public void DeleteTestProblem() {
        onData(anything()).inAdapterView((withId(R.id.problemListView)))
                .atPosition(0)
                .onChildView(withId(R.id.deleteImg))
                .perform(click());
        onView(withText("CONFIRM")).perform(click());
    }

    // Making sure correct dialog is launched when add record button is pressed
    @Test
    public void PRAddRecordTest() {
        onView(withId(R.id.addRecordsBtn)).perform(click());
        onView(withText("Add Record")).check(matches(isDisplayed()));
        onView(withText("CANCEL")).perform(click());
        pressBack();
    }

    // Making sure the logout button is working as intended
    @Test
    public void PRLogoutButtonTest() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Logout")).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
        onView(withId(R.id.userIdLogin))
                .perform(replaceText("walker2018"),closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Making sure the map button is working as intended
    @Test
    public void PRMapButtonTest() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Map")).perform(click());
        intended(hasComponent(MapActivity.class.getName()));
        pressBack();
        onView(withText("OKAY")).perform(click());
        pressBack();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Making sure view is updated when record added/deleted
    @Test
    public void PRRecordUpdateTest() {
        // Checking if added record is correctly displayed
        onView(withId(R.id.addRecordsBtn)).perform(click());
        onView(withHint("Title"))
                .perform(replaceText("TestRecord"));
        onView(withText("SAVE")).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pressBack();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("TestRecord")).check(matches(isDisplayed()));

        // Checking that view is updated after record deleted
        onData(anything()).inAdapterView((withId(R.id.recordListView)))
                .atPosition(0)
                .onChildView(withId(R.id.deleteImg))
                .perform(click());
        onView(withText("CONFIRM")).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("TestRecord")).check(doesNotExist());
        pressBack();
        pressBack();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
