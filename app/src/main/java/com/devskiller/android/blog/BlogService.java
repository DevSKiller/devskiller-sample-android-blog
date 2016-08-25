package com.devskiller.android.blog;

import com.devskiller.android.blog.app.model.Post;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Observable;

public class BlogService {

    private static BlogService ourInstance = new BlogService(new ArrayList<Post>());

    public static BlogService getInstance() {
        return ourInstance;
    }

    protected BlogService(List<Post> entries) {
        this.entries = entries;
    }

    private final List<Post> entries;

    public Observable<Post> getEntries() {
        return Observable.from(entries);
    }

    public void add(Post post) {
        entries.add(post);
    }

    public void remove(String title) {
        Iterator<Post> it = entries.iterator();
        while (it.hasNext()) {
            if (it.next().getTitle().equals(title)) {
                it.remove();
            }
        }
    }

}
