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
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.not;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class MapActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> mapActivityTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Before
    public void LoginCredentials() {
        // Assuming walker2018 is a user, which it should be
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.userIdLogin))
                .perform(replaceText("walker2018"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        try {
            Thread.sleep(2000);
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
        onView(withHint("Title")).perform(replaceText("TestRecord"));
        onView(withText("SAVE")).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void DeleteTestProblem() {
        pressBack();
        pressBack();
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

    // Making sure cancel button is working properly
    @Test
    public void MAPEditButtonsTest() {
        // Making sure cancel button is working properly
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.editmapbut)).perform(click());
        onView(withText("CANCEL")).perform(click());
        intended(hasComponent(PatientRecordViewActivity.class.getName()));
        onView(withId(R.id.editmapbut)).perform(click());
        onView(withText("SAVE")).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("Record is edited"))
                .inRoot(withDecorView(not(mapActivityTestRule.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

}
