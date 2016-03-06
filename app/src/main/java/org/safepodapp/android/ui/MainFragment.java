package org.safepodapp.android.ui;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;
import org.safepodapp.android.R;
import org.safepodapp.android.SafePodApplication;
import org.safepodapp.android.adapters.ForumViewPagerAdapter;
import org.safepodapp.android.beans.Experience;
import org.safepodapp.android.util.NetworkServices;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    private View view;
    private ArrayList<Experience> experiences = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

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
                    Experience experience = new Experience();
                    experience.setBody(o.getString("body"));
                    experience.setDay(o.getString("day"));
                    experience.setMonth(o.getString("month"));
                    experience.setZip(o.getString("zip"));
                    experience.setYear(o.getString("year"));
                    experience.setLatitude(o.getString("latitude"));
                    experience.setLongitude(o.getString("longitude"));
                    experience.setCity(o.getString("city"));
                    experience.setState(o.getString("state"));
                    experiences.add(experience);
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
            ForumViewPagerAdapter adapter = new ForumViewPagerAdapter(experiences);
            ViewPager myPager = (ViewPager) view.findViewById(R.id.myfivepanelpager);
            myPager.setAdapter(adapter);
            myPager.setCurrentItem(0);
        }
    }
}