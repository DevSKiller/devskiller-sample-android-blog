package com.devskiller.android.blog;

import com.devskiller.android.blog.BlogService;
import com.devskiller.android.blog.app.model.Post;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class BlogServiceTest {

    private BlogService blogService = new BlogService(new ArrayList<Post>());

    @Test
    public void shouldAddPost() throws Exception {
        //given
        String post_title_to_be_added = "to_be_added";

        //when
        blogService.add(new Post(post_title_to_be_added, "foo"));

        //then
        assertThat(posts())
                .hasSize(1)
                .extracting("title")
                .contains(post_title_to_be_added);
    }

    @Test
    public void shouldRemovePost() throws Exception {
        //given
        String post_title_to_be_deleted = "to_be_deleted";
        blogService = new BlogService(new ArrayList<>(singletonList(new Post(post_title_to_be_deleted, "foo"))));

        //when
        blogService.remove(post_title_to_be_deleted);

        //then
        assertThat(posts()).isEmpty();
    }

    private List<Post> posts() {
        final List<Post> posts = new ArrayList<>();
        blogService.getEntries().subscribe(new Action1<Post>() {
            @Override
            public void call(Post post) {
                posts.add(post);
            }
        });
        return posts;
    }
}
