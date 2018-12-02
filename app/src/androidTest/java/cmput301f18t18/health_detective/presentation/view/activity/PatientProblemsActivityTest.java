package cmput301f18t18.health_detective.presentation.view.activity;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cmput301f18t18.health_detective.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class PatientProblemsActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> ppActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void LoginCredentials() {
        onView(withId(R.id.userIdLogin))
                .perform(replaceText("walker2018"),closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
    }

    //Making sure correct activity is launched when add problem button is pressed
    @Test
    public void PPAddProblemTest() {
        // WHY THE FUCK ISNT THIS WORKING?!?!?
        onView(withId(R.id.addProbBtn)).perform(click());
        intended(hasComponent(ProblemEditAddActivity.class.getName()));
    }


    //Making sure the logout button is working as intended
    /*@Test
    public void PPLogoutButtonTest() {

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Logout")).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }*/
}
