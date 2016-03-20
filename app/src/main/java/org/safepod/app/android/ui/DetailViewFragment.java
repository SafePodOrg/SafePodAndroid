package org.safepod.app.android.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.safepod.app.android.R;
import org.safepod.app.android.SafePodApplication;
import org.safepod.app.android.beans.ForumPost;
import org.safepod.app.android.exceptions.AppSignatureNotGeneratedException;
import org.safepod.app.android.exceptions.DeviceIdNotGeneratedException;
import org.safepod.app.android.util.NetworkServices;

/**
 * Created by Prajit on 3/6/2016.
 */
public class DetailViewFragment extends Fragment {
    private View view;
    private SharedPreferences sharedPreferences;
    private String appSignKey;
    private String deviceId;
    private String postId;
    private StringBuffer commentsString;
    private ForumPost forumPost;
    private TextView textViewForumPost;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_posts_detail_view, container, false);

        sharedPreferences = view.getContext().getSharedPreferences(SafePodApplication.getSharedPreference(), Context.MODE_PRIVATE);
        commentsString = new StringBuffer();
        commentsString.append("\n");
        forumPost = new ForumPost();

        appSignKey = sharedPreferences.getString("appSignKey", "nokey");
        deviceId = sharedPreferences.getString("devId", "nodevid");
        postId = sharedPreferences.getString("currentPostId", "noid");
        new GetExperiences().execute();
        return view;
    }

    private void setInputData() throws DeviceIdNotGeneratedException, AppSignatureNotGeneratedException {
        sharedPreferences = view.getContext().getSharedPreferences(SafePodApplication.getSharedPreference(), Context.MODE_PRIVATE);
        appSignKey = sharedPreferences.getString(SafePodApplication.getUriVariableAppSignatureKey(),
                SafePodApplication.getErrorTag() + SafePodApplication.getUriVariableAppSignatureKey());
        deviceId = sharedPreferences.getString(SafePodApplication.getUriVariableDeviceIdentifierKey(),
                SafePodApplication.getErrorTag() + SafePodApplication.getUriVariableDeviceIdentifierKey());

        if (appSignKey.startsWith(SafePodApplication.getErrorTag()))
            throw new AppSignatureNotGeneratedException();
        if (deviceId.startsWith(SafePodApplication.getErrorTag()))
            throw new DeviceIdNotGeneratedException();
    }

    private class GetExperiences extends AsyncTask<Void, Integer, String> {
        protected void onPreExecute() {
            Log.d(SafePodApplication.getDebugTag(), "On pre Exceute......");
        }

        @Override
        protected String doInBackground(Void... arg0) {
            Log.d(SafePodApplication.getDebugTag(), "On doInBackground...");

            try {
                Log.d(SafePodApplication.getDebugTag(), SafePodApplication.getBaseUri() + "post/" + postId);
                String result = NetworkServices.sendGet(SafePodApplication.getBaseUri() +
                                SafePodApplication.getUriQuestionMark() +
                                SafePodApplication.getUriVariableAppSignature() +
                                appSignKey +
                                SafePodApplication.getUriAmpersand() +
                                SafePodApplication.getUriVariableDeviceIdentifier() +
                                deviceId +
                                SafePodApplication.getUriAmpersand() +
                                SafePodApplication.getUriVariablePostIdentifier() +
                                postId
                );
                //"http://safepodapp.org/forum/post/?sign=appSignKey&userid=deviceId&id=postId"
                JSONObject json = new JSONObject(result);
                JSONArray array = json.getJSONArray("results");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.getJSONObject(i);
                    forumPost.setBody(o.getString("body"));
                    forumPost.setId(o.getString("id"));
                    forumPost.setUpvotes(o.getString("likes"));
                    forumPost.setDownvotes(o.getString("dislikes"));
//                    forumPost.setId(o.getString("posted"));
//                    forumPost.setId(o.getString("liked"));
//                    forumPost.setId(o.getString("disliked"));
                    JSONArray a = o.getJSONArray("tags");
                    JSONArray comments = o.getJSONArray("comments");
                    for (int j = 0; j < comments.length(); j++) {
                        try {
                            commentsString.append(comments.getJSONObject(j).toString());
                            commentsString.append("\n");
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
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

        @Override
        protected void onPostExecute(String result) {
            TextView tv = (TextView) view.findViewById(R.id.postDetailTextView);
            tv.setText(forumPost.getBody());//+commentsString.toString());
        }
    }
}