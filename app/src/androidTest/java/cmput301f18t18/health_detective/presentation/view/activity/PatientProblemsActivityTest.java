package cmput301f18t18.health_detective.presentation.view.activity;


import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.model.Patient;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PatientProblemsActivityTest {

    MainActivity activity = new MainActivity();

    @Rule
    public ActivityTestRule<PatientProblemsActivity> ppActivityTestRule =
            new ActivityTestRule<>(PatientProblemsActivity.class,false,false);

    @Before
    public void LoginCredentials() {

        Patient p = new Patient("walker2018");
        activity.onLoginPatient(p);
        onView(withId(R.id.userIdLogin)).perform(replaceText("walker2018"));
        onView(withId(R.id.loginButton)).perform(click());
    }

    //Making sure the logout button is working as intended
    @Test
    public void PPLogoutButtonTest() {

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Logout")).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }
}
