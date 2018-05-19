package com.example.erik.eriknevilead340;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

import com.example.erik.eriknevilead340.MainActivity;
import com.example.erik.eriknevilead340.R;

public class TextEntryUI {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void uiElementExists() {
        onView(withId(R.id.editText)).check(matches(isDisplayed()));
        onView(withId(R.id.button)).check(matches(isDisplayed()));
    }

    @Test
    public void validTextEntry() {
        onView(withId(R.id.editText)).perform(typeText("word"), closeSoftKeyboard());
        onView(withId(R.id.button)).perform(click());
    }

}
