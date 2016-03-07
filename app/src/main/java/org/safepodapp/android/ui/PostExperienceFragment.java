package org.safepodapp.android.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.safepodapp.android.R;
import org.safepodapp.android.SafePodApplication;
import org.safepodapp.android.beans.ForumPost;
import org.safepodapp.android.util.NetworkServices;

public class PostExperienceFragment extends Fragment {
    private EditText postBody;
    private Button postButton;
    private SharedPreferences sharedPreferences;
    private ForumPost forumPost;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_experience, container, false);

        sharedPreferences = rootView.getContext().getSharedPreferences(SafePodApplication.getSharedPreference(), Context.MODE_PRIVATE);

        postBody = (EditText) rootView.findViewById(R.id.writePost);
        postButton = (Button) rootView.findViewById(R.id.post_experience_button);

        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.M)
            // only for versions older than Marhsmallow
            postBody.setTextColor(getResources().getColor(R.color.light));
        else
            postBody.setTextColor(getResources().getColor(R.color.light, rootView.getContext().getTheme()));

        postBody.setGravity(Gravity.LEFT);
        postBody.setTextSize(28);
        Typeface face = Typeface.createFromAsset(container.getContext().getAssets(), "JosefinSans-Regular.ttf");
        postBody.setTypeface(face);
        postBody.setLineSpacing(0.0f, 1.2f);

        forumPost = new ForumPost();
        forumPost.setBody(String.valueOf(postBody.getText()));
        forumPost.setAppSignature(sharedPreferences.getString("appSignKey", "nokey"));
        forumPost.setDeviceId(sharedPreferences.getString("devId", "nodevid"));

        postButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new PostExperience().execute();
                TopPostsFragment forumFragment = new TopPostsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(container.getId(), forumFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        return rootView;
    }

    class PostExperience extends AsyncTask<Void, Integer, String> {
        protected void onPreExecute() {
            Log.d(SafePodApplication.getDebugTag(), "On pre Exceute......");
        }

        protected String doInBackground(Void... arg0) {
            Log.d(SafePodApplication.getDebugTag(), "On doInBackground...");

            Gson gson = new Gson();
            String json = gson.toJson(forumPost);

            try {
                NetworkServices.sendPost(SafePodApplication.getBaseUri() + "post/new", json);
                //"http://safepodapp.org/forum/post/new/json"
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

            Toast.makeText(getActivity(),
                    "Your post has been sent!",
                    Toast.LENGTH_LONG).show();
        }
    }

}