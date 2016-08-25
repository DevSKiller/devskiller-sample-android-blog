package com.devskiller.android.blog;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Whitelist;

public class PostItem {

    private String title;

    private String content;

    public PostItem(String title, String content) {
        this.title = title;
        this.content = Parser.unescapeEntities(Jsoup.clean(content, Whitelist.none()), false);
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
