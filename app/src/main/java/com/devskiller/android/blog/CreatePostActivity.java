package com.devskiller.android.blog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreatePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.devskiller.android.blog.R.layout.activity_create_post);

        final Button submit = (Button) findViewById(com.devskiller.android.blog.R.id.button_submit);
        final EditText title = (EditText) findViewById(com.devskiller.android.blog.R.id.newTitle);
        final EditText content = (EditText) findViewById(com.devskiller.android.blog.R.id.newContent);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // 1. Set error when title is empty
                // 2. Set error when content is empty
                // 3. Return title and content to MainActivity
                // 4. Go back to main screen

                Intent resultIntent = new Intent();
                resultIntent.putExtra("title", title.getText().toString());
                resultIntent.putExtra("content", content.getText().toString());
                setResult(Activity.RESULT_OK, resultIntent);

            }
        });
    }


}
