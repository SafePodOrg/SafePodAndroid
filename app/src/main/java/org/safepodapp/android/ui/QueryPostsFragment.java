package org.safepodapp.android.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.safepodapp.android.R;
import org.safepodapp.android.SafePodApplication;
import org.safepodapp.android.adapters.ForumPostsListAdapter;
import org.safepodapp.android.beans.ForumPost;
import org.safepodapp.android.exceptions.AppSignatureNotGeneratedException;
import org.safepodapp.android.exceptions.DeviceIdNotGeneratedException;
import org.safepodapp.android.util.NetworkServices;

import java.util.ArrayList;

public class QueryPostsFragment extends Fragment {
    private View view;
    private SharedPreferences sharedPreferences;
    private String appSignKey;
    private String deviceId;
    private String searchQuery;
    private ArrayList<ForumPost> forumPosts = new ArrayList<>();
    private ListView listViewForumPosts;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_query_posts, container, false);
        Log.d(SafePodApplication.getDebugTag(), "I came here!");
//        try {
//            setInputData();
//        } catch(DeviceIdNotGeneratedException e) {
//            Log.e(SafePodApplication.getDebugTag(),e.getMessage());
//            return view;
//        } catch (AppSignatureNotGeneratedException e) {
//            Log.e(SafePodApplication.getDebugTag(),e.getMessage());
//            return view;
//        }
        new GetExperiences().execute();
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.remove("query");
//        editor.commit();
        return view;
    }

    private void setInputData() throws DeviceIdNotGeneratedException, AppSignatureNotGeneratedException {
        sharedPreferences = view.getContext().getSharedPreferences(SafePodApplication.getSharedPreference(), Context.MODE_PRIVATE);
        appSignKey = sharedPreferences.getString(SafePodApplication.getUriVariableAppSignatureKey(),
                SafePodApplication.getErrorTag() + SafePodApplication.getUriVariableAppSignatureKey());
        deviceId = sharedPreferences.getString(SafePodApplication.getUriVariableDeviceIdentifierKey(),
                SafePodApplication.getErrorTag() + SafePodApplication.getUriVariableDeviceIdentifierKey());
        searchQuery = new String(sharedPreferences.getString("query", ""));

        if (appSignKey.startsWith(SafePodApplication.getErrorTag()))
            throw new AppSignatureNotGeneratedException();
        if (deviceId.startsWith(SafePodApplication.getErrorTag()))
            throw new DeviceIdNotGeneratedException();
    }

    private class GetExperiences extends AsyncTask<Void, Integer, String> {
//        ArrayList<ForumPost> forumPosts = new ArrayList<ForumPost>();

        protected void onPreExecute() {
            System.out.println("On pre Exceute......");
        }

        protected String doInBackground(Void... arg0) {
            System.out.println("On doInBackground...");

            try {
                String result = NetworkServices.sendGet(SafePodApplication.getBaseUri() +
                                SafePodApplication.getUriSlash() +
                                SafePodApplication.getUriSearch() +
                                SafePodApplication.getUriQuestionMark() +
                                SafePodApplication.getUriVariableAppSignature() +
                                "123" +//appSignKey +
                                SafePodApplication.getUriAmpersand() +
                                SafePodApplication.getUriVariableDeviceIdentifier() +
                                "123" +//deviceId +
                                SafePodApplication.getUriAmpersand() +
                                SafePodApplication.getUriVariableSearchQueryText() +
                                "pkd"
                );
                //"http://safepodapp.org/forum/search/?sign=appSignKey&userid=deviceId&q=searchQuery"
                JSONObject json = new JSONObject(result);
                JSONArray array = json.getJSONArray("results");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.getJSONObject(i);
                    ForumPost forumPost = new ForumPost();
                    forumPost.setBody(o.getString("body"));
                    forumPost.setId(o.getString("id"));
                    JSONArray a = o.getJSONArray("tags");
                    forumPost.setTags(a);
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
            listViewForumPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Fragment fragment = new DetailViewFragment();

//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("currentPostId", forumPosts.get(position).getId());
//                    editor.commit();

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment)
                            .commit();
                }
            });
        }
    }
}