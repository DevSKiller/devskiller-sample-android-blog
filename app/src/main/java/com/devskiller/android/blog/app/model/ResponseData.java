package com.devskiller.android.blog.app.model;

public class ResponseData {
    private Feed feed;

    public ResponseData(Feed feed) {
        this.feed = feed;
    }

    public Feed getFeed() {
        return feed;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "feed=" + feed +
                '}';
    }
}
