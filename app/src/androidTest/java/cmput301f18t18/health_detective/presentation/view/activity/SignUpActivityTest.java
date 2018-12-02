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
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertTrue;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {

    @Rule
    public IntentsTestRule<SignUpActivity> suActivityTestRule =
            new IntentsTestRule<>(SignUpActivity.class);

    //Making sure invalid data invokes correct toast and does not change activities
    @Test
    public void SUInvalidDataTest() {
        //Checking invalid ID
        onView(withId(R.id.userEdit))
                .perform(replaceText("walker"),closeSoftKeyboard());
        onView(withId(R.id.emailEdit))
                .perform(replaceText("validemail@email.com"),closeSoftKeyboard());
        onView(withId(R.id.phoneNumEdit))
                .perform(replaceText("(780) 222-2222"),closeSoftKeyboard());
        onView(withId(R.id.signUpBtn)).perform(click());
        onView(withText("Invalid Id"))
                .inRoot(withDecorView(not(suActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        //Checking invalid Email
        onView(withId(R.id.userEdit))
                .perform(replaceText("spongebob99"),closeSoftKeyboard());
        onView(withId(R.id.emailEdit))
                .perform(replaceText("invalidemail"),closeSoftKeyboard());
        onView(withId(R.id.phoneNumEdit))
                .perform(replaceText("(780) 222-2222"),closeSoftKeyboard());
        onView(withId(R.id.signUpBtn)).perform(click());
        onView(withText("Invalid Email"))
                .inRoot(withDecorView(not(suActivityTestRule .getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        //Checking invalid phone number
        onView(withId(R.id.userEdit))
                .perform(replaceText("spongebob99"),closeSoftKeyboard());
        onView(withId(R.id.emailEdit))
                .perform(replaceText("validemail@email.com"),closeSoftKeyboard());
        onView(withId(R.id.phoneNumEdit))
                .perform(replaceText("7802222222"),closeSoftKeyboard());
        onView(withId(R.id.signUpBtn)).perform(click());
        onView(withText("Invalid Phone Number"))
                .inRoot(withDecorView(not(suActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

    }

    //Making sure cancel button is working properly
    @Test
    public void SUCancelTest() {
        onView(withId(R.id.cancelBtn)).perform(click());
        assertTrue(suActivityTestRule.getActivity().isFinishing());
    }

    //Making sure the sign up button stores info and sends you to correct activity
    @Test
    public void SUAcceptTest() {
        //Generating random string for userID (for uniqueness)
        int n = 10000000 + (int)(Math.random() * 90000000);
        String userID = Integer.toString(n);
        onView(withId(R.id.userEdit))
                .perform(replaceText(userID),closeSoftKeyboard());
        onView(withId(R.id.emailEdit))
                .perform(replaceText("validemail@email.com"),closeSoftKeyboard());
        onView(withId(R.id.phoneNumEdit))
                .perform(replaceText("(780) 222-2222"),closeSoftKeyboard());
        onView(withId(R.id.signUpBtn)).perform(click());
        /*
        Have to use withText instead of withID because view is in menu bar.
        This also ensure we are in right activity because this would not be
        in the view if we were not in the correct activity
        */
        onView(withText(userID)).check(matches(withText(userID)));
    }
}
