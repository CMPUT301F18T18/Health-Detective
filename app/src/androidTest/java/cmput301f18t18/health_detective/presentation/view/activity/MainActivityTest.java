//package cmput301f18t18.health_detective.presentation.view.activity;
//
//
//import android.support.test.espresso.ViewInteraction;
//import android.support.test.filters.LargeTest;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//
//import org.hamcrest.Description;
//import org.hamcrest.Matcher;
//import org.hamcrest.TypeSafeMatcher;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import cmput301f18t18.health_detective.R;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.Espresso.pressBack;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static android.support.test.espresso.action.ViewActions.replaceText;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//import static org.hamcrest.Matchers.allOf;
//
//@LargeTest
//@RunWith(AndroidJUnit4.class)
//public class MainActivityTest {
//
//    @Rule
//    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
//
//    @Test
//    public void mainActivityTest() {
//        ViewInteraction appCompatTextView = onView(
//                allOf(withId(R.id.signUpText), withText("Sign up"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                2),
//                        isDisplayed()));
//        appCompatTextView.perform(click());
//
//        ViewInteraction appCompatEditText = onView(
//                allOf(withId(R.id.userEdit),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                2),
//                        isDisplayed()));
//        appCompatEditText.perform(replaceText("Da"), closeSoftKeyboard());
//
//        pressBack();
//
//        ViewInteraction appCompatEditText2 = onView(
//                allOf(withId(R.id.userEdit), withText("Da"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                2),
//                        isDisplayed()));
//        appCompatEditText2.perform(click());
//
//        ViewInteraction appCompatEditText3 = onView(
//                allOf(withId(R.id.userEdit), withText("Da"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                2),
//                        isDisplayed()));
//        appCompatEditText3.perform(click());
//
//        ViewInteraction appCompatEditText4 = onView(
//                allOf(withId(R.id.userEdit), withText("Da"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                2),
//                        isDisplayed()));
//        appCompatEditText4.perform(replaceText("Dalin"));
//
//        ViewInteraction appCompatEditText5 = onView(
//                allOf(withId(R.id.userEdit), withText("Dalin"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                2),
//                        isDisplayed()));
//        appCompatEditText5.perform(closeSoftKeyboard());
//
//        pressBack();
//
//        ViewInteraction appCompatEditText6 = onView(
//                allOf(withId(R.id.userEdit), withText("Dalin"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                2),
//                        isDisplayed()));
//        appCompatEditText6.perform(replaceText("Dalinrichesh"));
//
//        ViewInteraction appCompatEditText7 = onView(
//                allOf(withId(R.id.userEdit), withText("Dalinrichesh"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                2),
//                        isDisplayed()));
//        appCompatEditText7.perform(closeSoftKeyboard());
//
//        ViewInteraction editText = onView(
//                allOf(withId(R.id.userEdit), withText("Dalinrichesh"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                2),
//                        isDisplayed()));
//        editText.check(matches(withText("Dalinrichesh")));
//    }
//
//    private static Matcher<View> childAtPosition(
//            final Matcher<View> parentMatcher, final int position) {
//
//        return new TypeSafeMatcher<View>() {
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("Child at position " + position + " in parent ");
//                parentMatcher.describeTo(description);
//            }
//
//            @Override
//            public boolean matchesSafely(View view) {
//                ViewParent parent = view.getParent();
//                return parent instanceof ViewGroup && parentMatcher.matches(parent)
//                        && view.equals(((ViewGroup) parent).getChildAt(position));
//            }
//        };
//    }
//}
