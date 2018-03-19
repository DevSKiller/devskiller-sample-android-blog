package verify_pack;

import android.app.Activity;
import android.widget.EditText;

import com.devskiller.android.blog.BuildConfig;
import com.devskiller.android.blog.CreatePostActivity;
import com.devskiller.android.blog.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AddPostTest {

    private CreatePostActivity activity;
    private ShadowActivity shadowActivity;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(CreatePostActivity.class);
        shadowActivity = shadowOf(activity);
    }

    @Test
    public void shouldSubmit() {

        // given
        setEditText("Foo", R.id.newTitle);
        setEditText("Bar", R.id.newContent);

        // when
        activity.findViewById(R.id.button_submit).performClick();

        // then
        assertThat(shadowActivity.getResultCode()).isEqualTo(Activity.RESULT_OK);

    }
    @Test
    public void shouldFailToSubmitWhenDataIsIncomplete() {

        // given
        setEditText("Foo", R.id.newTitle);

        // when
        activity.findViewById(R.id.button_submit).performClick();

        // then
        assertThat(shadowActivity.getResultCode()).isEqualTo(Activity.RESULT_CANCELED);
        assertThat(((EditText) activity.findViewById(R.id.newContent)).getError()).isNotEmpty();
    }


    private void setEditText(String value, int id) {
        EditText title = activity.findViewById(id);
        title.setText(value);
    }

}
