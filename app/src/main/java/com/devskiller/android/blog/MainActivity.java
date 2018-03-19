package com.devskiller.android.blog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.devskiller.android.blog.app.BlogAdapter;
import com.devskiller.android.blog.app.model.Post;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.devskiller.android.blog.R.id.main_view;

public class MainActivity extends AppCompatActivity {

    public static final int POST_CREATED = 200;
    private String TAG = MainActivity.class.getSimpleName();

    private SwipeRefreshLayout swipeRefreshLayout;
    private BlogAdapter adapter;
    private BlogService service;

    private Subscription subscription;

    private Observable<Post> observable;

    private final Observer<Post> observer = new Observer<Post>() {
        @Override
        public void onCompleted() {
            if (swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Post post) {
            adapter.update(new PostItem(post.getTitle(), post.getContent()));
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            service.add(new Post(title, content));
            Snackbar.make(findViewById(main_view), String.format("Post '%s' submitted", title), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            refresh();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.devskiller.android.blog.R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        service = BlogService.getInstance();
        service.add(new Post("Foo", "bar ek"));
        service.add(new Post("Bar", "bar2"));

        observable = service.getEntries().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        FloatingActionButton addPostButton = findViewById(R.id.addPost);

        // 1. Click on addPostButton should open CreatePostActivity

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        adapter = new BlogAdapter();

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PostItem itemAtPosition = (PostItem) parent.getItemAtPosition(position);
                service.remove(itemAtPosition.getTitle());
                refresh();
                return true;
            }
        });
        swipeRefreshLayout.setRefreshing(true);
        subscription = observable.subscribe(observer);


    }

    private void refresh() {
        if (!subscription.isUnsubscribed())
            subscription.unsubscribe();
        adapter.clear();
        subscription = observable.subscribe(observer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.devskiller.android.blog.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return item.getItemId() == com.devskiller.android.blog.R.id.action_settings || super.onOptionsItemSelected(item);
    }


}
