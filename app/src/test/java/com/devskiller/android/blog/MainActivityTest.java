package com.devskiller.android.blog;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static org.assertj.android.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private ActivityController<MainActivity> controller;
    private MainActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void shouldCreatePost() {

        // given
        activity.findViewById(R.id.addPost).performClick();

        // then
        Intent expectedIntent = new Intent(activity, CreatePostActivity.class);
        assertThat(shadowOf(activity).getNextStartedActivity()).isEqualTo(expectedIntent);
    }


}
