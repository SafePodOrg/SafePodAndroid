package org.safepodapp.android.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.safepodapp.android.R;
import org.safepodapp.android.SafePodApplication;
import org.safepodapp.android.adapters.ForumPostsListAdapter;
import org.safepodapp.android.beans.ForumPost;
import org.safepodapp.android.util.NetworkServices;

import java.util.ArrayList;

public class QueryPostsFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private View view;
    private ArrayList<ForumPost> forumPosts = new ArrayList<>();
    private ListView listViewForumPosts;
    private String searchQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_query_posts, container, false);

        sharedPreferences = view.getContext().getSharedPreferences(SafePodApplication.getSharedPreference(), Context.MODE_PRIVATE);
        searchQuery = new String(sharedPreferences.getString("query", ""));
        Log.d(SafePodApplication.getDebugTag(), "Query is:" + searchQuery);

        new GetExperiences().execute();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("query");
        editor.commit();

        return view;
    }

    private class GetExperiences extends AsyncTask<Void, Integer, String> {
        ArrayList<ForumPost> forumPosts = new ArrayList<ForumPost>();

        protected void onPreExecute() {
            System.out.println("On pre Exceute......");
        }

        protected String doInBackground(Void... arg0) {
            System.out.println("On doInBackground...");

            try {
                Log.d(SafePodApplication.getDebugTag(), searchQuery.toString());
                String result = NetworkServices.sendGet("http://safepodapp.org/forum?q=" + searchQuery.toString());
                JSONObject json = new JSONObject(result);
                JSONArray array = json.getJSONArray("results");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.getJSONObject(i);
                    ForumPost forumPost = new ForumPost();
                    forumPost.setBody(o.getString("body"));
                    forumPosts.add(forumPost);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            for (int i = 0; i < 10; i++) {
                publishProgress(i);
            }
            return "You are at PostExecute";
        }

        protected void onProgressUpdate(Integer... a) {
        }

        protected void onPostExecute(String result) {
            listViewForumPosts = (ListView) view.findViewById(R.id.listViewQueryPosts);
            listViewForumPosts.setAdapter(new ForumPostsListAdapter(view.getContext(), R.layout.posts_list_item, forumPosts));
//            ForumViewPagerAdapter adapter = new ForumViewPagerAdapter(experiences);
//            ViewPager myPager = (ViewPager) findViewById(R.id.myfivepanelpager);
//            myPager.setAdapter(adapter);
//            myPager.setCurrentItem(0);
        }
    }
}