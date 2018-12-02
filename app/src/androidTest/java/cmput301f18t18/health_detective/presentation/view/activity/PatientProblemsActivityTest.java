package cmput301f18t18.health_detective.presentation.view.activity;


import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cmput301f18t18.health_detective.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PatientProblemsActivityTest {

    @Rule
    public IntentsTestRule<PatientProblemsActivity> ppActivityTestRule =
            new IntentsTestRule<>(PatientProblemsActivity.class);

    //Making sure the logout button is working as intended
    @Test
    public void PPLogoutButtonTest() {
        onView(withId(R.id.Logout_option)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }


}
