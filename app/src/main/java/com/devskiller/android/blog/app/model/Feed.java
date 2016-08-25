package com.devskiller.android.blog.app.model;

import java.util.List;

public class Feed {

    private final List<Post> entries;

    public Feed(List<Post> entries) {
        this.entries = entries;
    }

    public List<Post> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "entries=" + entries +
                '}';
    }
}
