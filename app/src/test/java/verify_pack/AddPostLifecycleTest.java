package verify_pack;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.devskiller.android.blog.BuildConfig;
import com.devskiller.android.blog.CreatePostActivity;
import com.devskiller.android.blog.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.assertj.android.api.Assertions.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class AddPostLifecycleTest {

    private ActivityController<CreatePostActivity> controller;
    private CreatePostActivity activity;

    @Before
    public void setUp() {
        controller = Robolectric.buildActivity(CreatePostActivity.class);
        activity = controller
                .create()
                .start()
                .resume()
                .visible()
                .get();
    }

    @After
    public void tearDown() {
        controller
                .pause()
                .stop()
                .destroy();
    }

    @Test
    public void shouldKeepValuesAfterActivityPauseAndResume() {

        // given
        setEditText("Foo", R.id.newTitle);
        setEditText("Bar", R.id.newContent);

        // when
        controller.pause().resume();

        // then
        assertThat((TextView) activity.findViewById(R.id.newTitle)).hasText("Foo");
        assertThat((TextView) activity.findViewById(R.id.newContent)).hasText("Bar");
    }

    @Test
    public void shouldKeepValuesAfterActivityRecreation() {

        // given
        setEditText("Foo", R.id.newTitle);
        setEditText("Bar", R.id.newContent);

        Bundle bundle = new Bundle();

        // when
        destroyOriginalActivity(bundle);
        bringUpOriginalActivity(bundle);

        // then
        assertThat((TextView) activity.findViewById(R.id.newTitle)).hasText("Foo");
        assertThat((TextView) activity.findViewById(R.id.newContent)).hasText("Bar");
    }

    private void setEditText(String value, int id) {
        EditText title = (EditText) activity.findViewById(id);
        title.setText(value);
    }

    private void bringUpOriginalActivity(Bundle bundle) {
        controller = Robolectric.buildActivity(CreatePostActivity.class)
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
