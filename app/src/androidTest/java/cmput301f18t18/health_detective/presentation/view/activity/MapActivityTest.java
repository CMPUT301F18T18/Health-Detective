package cmput301f18t18.health_detective.presentation.view.activity;


import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import cmput301f18t18.health_detective.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class MapActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> mapActivityTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Before
    public void LoginCredentials() {
        // Assuming walker2018 is a user, which it should be
        onView(withId(R.id.userIdLogin))
                .perform(replaceText("walker2018"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
    }

}
