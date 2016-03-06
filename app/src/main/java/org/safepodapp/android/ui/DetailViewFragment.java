package org.safepodapp.android.ui;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.safepodapp.android.R;
import org.safepodapp.android.SafePodApplication;
import org.safepodapp.android.beans.ForumPost;
import org.safepodapp.android.util.NetworkServices;

/**
 * Created by Prajit on 3/6/2016.
 */
public class DetailViewFragment extends Fragment {
    private View view;
    private ForumPost forumPost;
    private TextView textViewForumPost;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_posts_detail_view, container, false);

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
                String result = NetworkServices.sendGet("http://safepodapp.org/forum?"); // TODO call with id
                JSONObject json = new JSONObject(result);
                JSONArray array = json.getJSONArray("results");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.getJSONObject(i);
                    ForumPost forumPost = new ForumPost();
                    forumPost.setBody(o.getString("body"));
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
        }
    }

}
