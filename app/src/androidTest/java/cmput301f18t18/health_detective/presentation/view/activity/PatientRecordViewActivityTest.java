package cmput301f18t18.health_detective.presentation.view.activity;


import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class PatientRecordViewActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> prvActivityTestRule =
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
        onView(withId(R.id.addRecordsBtn)).perform(click());
        onView(withHint("Title"))
                .perform(replaceText("TestRecord"));
        onView(withText("SAVE")).perform(click());
    }

    @After
    public void DeleteTestProblem() {
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
    }

    // Testing to make sure data is displaying correctly
    @Test
    public void PRVDataDisplayedCorrectTest() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("TestRecord")).check(matches(isDisplayed()));
        onView(withText("Body Location Pictures")).check(matches(isDisplayed()));
        onView(withText("Photos")).check(matches(isDisplayed()));
        onView(withText("Description")).check(matches(isDisplayed()));
        onView(withId(R.id.editmapbut)).check(matches(isDisplayed()));
        onView(withId(R.id.addNPhoto)).check(matches(isDisplayed()));
        pressBack();
        pressBack();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Testing to make sure edit location button is working correctly
    @Test
    public void PRVEditMapTest() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.editmapbut)).perform(click());
        intended(hasComponent(MapActivity.class.getName()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("CANCEL")).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pressBack();
        pressBack();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Making sure edit options in menu bar work
    @Test
    public void PRVMenuBarTest() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Edit Title")).perform(click());
        onView(withText("Edit Title")).check(matches(isDisplayed()));
        onView(withText("CANCEL")).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Edit Description")).perform(click());
        onView(withText("Edit Description")).check(matches(isDisplayed()));
        onView(withText("CANCEL")).perform(click());
        pressBack();
        pressBack();
        pressBack();
    }
}
