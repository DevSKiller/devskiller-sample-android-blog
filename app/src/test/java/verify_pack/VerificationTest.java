package verify_pack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.devskiller.android.blog.BuildConfig;
import com.devskiller.android.blog.CreatePostActivity;
import com.devskiller.android.blog.MainActivity;
import com.devskiller.android.blog.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowListView;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.util.ActivityController;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class VerificationTest {

    private ActivityController<MainActivity> controller;
    private MainActivity activity;
    private ListView listView;

    @Before
    public void setUp() {
        controller = Robolectric.buildActivity(MainActivity.class);
        activity = controller
                .create()
                .start()
                .resume()
                .visible()
                .get();

        listView = (ListView) activity.findViewById(R.id.listView);
        ShadowLog.stream = System.out;

    }

    @After
    public void tearDown() {
        controller
                .pause()
                .stop()
                .destroy();
    }

    @Test
    public void shouldCreatePost() {

        // given
        activity.findViewById(R.id.addPost).performClick();

        // then
        Intent expectedIntent = new Intent(activity, CreatePostActivity.class);
        assertThat(shadowOf(activity).getNextStartedActivity()).isEqualTo(expectedIntent);
    }

    @Test
    public void shouldKeepValuesAfterActivityRecreation() {

        // given
        ShadowListView shadowListView = shadowOf(listView);
        shadowListView.populateItems();
        int old = listView.getAdapter().getCount();

        Bundle bundle = new Bundle();

        // when
        destroyOriginalActivity(bundle);
        bringUpOriginalActivity(bundle);

        // then
        assertThat(listView.getAdapter().getCount()).isEqualTo(old);

    }

    @Test
    public void shouldKeepValuesAfterActivityPauseAndResume() {

        // given
        ShadowListView shadowListView = shadowOf(listView);
        shadowListView.populateItems();
        int old = listView.getAdapter().getCount();

        // when
        controller.pause().resume();

        // then
        assertThat(listView.getAdapter().getCount()).isEqualTo(old);

        // then
        assertThat(listView.getAdapter()).isNotEmpty();
    }


    private void bringUpOriginalActivity(Bundle bundle) {
        controller = Robolectric.buildActivity(MainActivity.class)
                .create(bundle)
                .start()
                .restoreInstanceState(bundle)
                .resume()
                .visible();
        activity = controller.get();
    }

    private void destroyOriginalActivity(Bundle bundle) {
        controller
                .saveInstanceState(bundle)
                .pause()
                .stop()
                .destroy();
    }

}
