package org.safepod.app.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.safepod.app.android.R;
import org.safepod.app.android.beans.ForumPost;

import java.util.List;

public class ForumPostsListAdapter extends ArrayAdapter<ForumPost> {
    private List<ForumPost> postList;
    private Context context;

    public ForumPostsListAdapter(Context context, int resource,
                                 List<ForumPost> objects) {
        super(context, resource, objects);

        this.context = context;
        this.postList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.posts_list_item, null);
        }

        ForumPost data = postList.get(position);

        if (data != null) {
            TextView postTitle = (TextView) view.findViewById(R.id.textViewPostTags);
            TextView postBody = (TextView) view.findViewById(R.id.textViewPostBody);

            postTitle.setText(data.getTags());
            postBody.setText(data.getBody());
        }
        return view;
    }
}