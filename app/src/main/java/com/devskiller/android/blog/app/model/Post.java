package com.devskiller.android.blog.app.model;

public class Post {

    private String title;

    private String content;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "PostItem{" +
                "title='" + title + '\'' +
                '}';
    }
}
