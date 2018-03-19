package com.devskiller.android.blog.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devskiller.android.blog.PostItem;
import com.devskiller.android.blog.R;

import java.util.ArrayList;
import java.util.List;

public class BlogAdapter extends BaseAdapter {

    private List<PostItem> items;

    public BlogAdapter() {
        this.items = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, null);
        }
        ViewHolder viewholder = new ViewHolder(convertView);
        viewholder.title.setText(items.get(position).getTitle());
        viewholder.content.setText(items.get(position).getContent());
        return convertView;
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void update(PostItem postItem) {
        items.add(postItem);
        notifyDataSetChanged();
    }

    static class ViewHolder {

        TextView title;
        TextView content;

        public ViewHolder(View view) {
            title = view.findViewById(R.id.title);
            content = view.findViewById(R.id.content);
        }
    }

}
