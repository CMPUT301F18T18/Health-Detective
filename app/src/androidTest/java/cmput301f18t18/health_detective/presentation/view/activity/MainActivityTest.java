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
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertTrue;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> mainActivityTestRule =
            new IntentsTestRule<>(MainActivity.class);

    //Making sure invalid Id invokes correct toast and does not change activities
    @Test
    public void MainInvalidDataTest() {
        //Checking invalid ID
        onView(withId(R.id.userIdLogin))
                .perform(replaceText("walker"),closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        onView(withText("Invalid UserId"))
                .inRoot(withDecorView(not(mainActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

    }

    //Making sure Sign up button is working properly and launching correct activity
    @Test
    public void MainSignUpButtonTest() {
        onView(withId(R.id.signUpText)).perform(click());
        intended(hasComponent(SignUpActivity.class.getName()));
    }

    //Making sure the login button logs in correct user and launches correct activity
    @Test
    public void MainLogInTest() {
        onView(withId(R.id.userIdLogin))
                .perform(replaceText("walker2018"),closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        onView(withText("Logging in"))
                .inRoot(withDecorView(not(mainActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        intended(hasComponent(PatientProblemsActivity.class.getName()));
    }
}
