package org.safepodapp.android.ui;

import android.app.Fragment;
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

public class MyPostsFragment extends Fragment {
    private View view;
    private ArrayList<ForumPost> forumPosts = new ArrayList<>();
    private ListView listViewForumPosts;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_posts, container, false);

        new GetExperiences().execute();
        return view;
    }

    private class GetExperiences extends AsyncTask<Void, Integer, String> {
        protected void onPreExecute() {
            Log.d(SafePodApplication.getDebugTag(), "On pre Exceute......");
        }

        protected String doInBackground(Void... arg0) {
            Log.d(SafePodApplication.getDebugTag(), "On doInBackground...");

            try {
                String result = NetworkServices.sendGet("http://safepodapp.org/forum?");
                JSONObject json = new JSONObject(result);
                JSONArray array = json.getJSONArray("results");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.getJSONObject(i);
                    ForumPost forumPost = new ForumPost();
                    forumPost.setBody(o.getString("body"));
//                    forumPost.setDay(o.getString("day"));
//                    forumPost.setMonth(o.getString("month"));
//                    forumPost.setZip(o.getString("zip"));
//                    forumPost.setYear(o.getString("year"));
//                    forumPost.setLatitude(o.getString("latitude"));
//                    forumPost.setLongitude(o.getString("longitude"));
//                    forumPost.setCity(o.getString("city"));
//                    forumPost.setState(o.getString("state"));
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
            listViewForumPosts = (ListView) view.findViewById(R.id.listViewMyPosts);
            listViewForumPosts.setAdapter(new ForumPostsListAdapter(view.getContext(), R.layout.posts_list_item, forumPosts));
//            ForumViewPagerAdapter adapter = new ForumViewPagerAdapter(forumPosts);
//            ViewPager myPager = (ViewPager) view.findViewById(R.id.myfivepanelpager);
//            myPager.setAdapter(adapter);
//            myPager.setCurrentItem(0);
        }
    }
}