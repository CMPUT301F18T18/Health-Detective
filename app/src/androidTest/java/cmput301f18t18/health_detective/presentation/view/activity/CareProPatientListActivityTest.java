package cmput301f18t18.health_detective.presentation.view.activity;


import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cmput301f18t18.health_detective.R;

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
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class CareProPatientListActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> cpActivityTestRule =
            new IntentsTestRule<>(MainActivity.class);


    @Before
    public void LoginCredentials() {
        // Assuming reklaw2018 is a user, which it should be
        onView(withId(R.id.userIdLogin))
                .perform(replaceText("reklaw2018"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("OKAY")).perform(click());
        onView(withText("OKAY")).perform(click());
        onView(withText("OKAY")).perform(click());
    }

    // Making sure logout option in menu bar works
    @Test
    public void CPMenuBarTest() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Logout")).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

    // Making sure view is updated when patient added/deleted
    @Test
    public void CPUpdatePatientsTest() {
        // Making sure cancel button is working properly
        onView(withId(R.id.addPatientBtn)).perform(click());
        onView(withHint("Patient"))
                .perform(replaceText("walker2018"));
        onView(withText("CANCEL")).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("walker2018")).check(doesNotExist());
        // Making sure add button is working properly
        onView(withId(R.id.addPatientBtn)).perform(click());
        onView(withHint("Patient"))
                .perform(replaceText("walker2018"));
        onView(withText("ADD")).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("walker2018")).check(matches(isDisplayed()));
        // Making sure delete patient button is working properly
        onData(anything()).inAdapterView((withId(R.id.patientListView)))
                .atPosition(0)
                .onChildView(withId(R.id.deleteImg))
                .perform(click());
        onView(withText("CONFIRM")).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("walker2018")).check(doesNotExist());
    }
}
