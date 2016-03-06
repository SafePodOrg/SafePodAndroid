package org.safepodapp.android.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import org.safepodapp.android.R;
import org.safepodapp.android.SafePodApplication;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        createShortCut();
//        setDefaultSharedPreferences();
        setUIElements();
    }

    private void setUIElements() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostExperienceFragment postExperienceFragment = new PostExperienceFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.container, postExperienceFragment);
                transaction.addToBackStack(null);

                transaction.commit();
                /**
                 * Providing lightweight, quick feedback about an operation is a perfect opportunity to use a snackbar. Snackbars are shown on the bottom of the screen and contain text with an optional single action.
                 * They automatically time out after the given time length by animating off the screen. In addition, users can swipe them away before the timeout.
                 */
//				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//						.setAction("Action", null).show();
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        defaultFragmentLoad();
    }

    private void defaultFragmentLoad() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, new MainFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_forum) {
            // Handle the camera action
            Log.d(SafePodApplication.getDebugTag(), "I came into the listener");
            fragment = new MainFragment();
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
        } else if (id == R.id.nav_about) {
            //TODO Have to generate the right fragment and attach it here
//            fragment = new MainFragment();
        } else if (id == R.id.nav_questions) {
            //TODO Have to generate the right fragment and attach it here
//            fragment = new MainFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        // update the main content by replacing fragments
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment)
                    .commit();
        } else {
            Log.e(SafePodApplication.getDebugTag(), "Error");
        }

        return true;
    }

//    public CharSequence getmTitle() {
//        return mTitle;
//    }
//    public void setmTitle(CharSequence mTitle) {
//        this.mTitle = mTitle;
//    }
//
//    public void onSectionAttached(int number) {
//        switch (number) {
//            case 1:
//                setmTitle(getString(R.string.title_section1));
//                break;
//            case 2:
//                setmTitle(getString(R.string.title_section2));
//                break;
//            case 3:
//                setmTitle(getString(R.string.title_section3));
//                break;
//        }
//    }

    private void setDefaultSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = 23; //Default expectation is you will reach home at 11
        int minute = 0;

        if (sharedPreferences.getInt(SafePodApplication.getYear(), 1970) == 1970)
            editor.putInt(SafePodApplication.getYear(), year);
        if (sharedPreferences.getInt(SafePodApplication.getMonth(), 1) == 1)
            editor.putInt(SafePodApplication.getMonth(), month);
        if (sharedPreferences.getInt(SafePodApplication.getDayOfMonth(), 1) == 1)
            editor.putInt(SafePodApplication.getDayOfMonth(), day);
        if (sharedPreferences.getInt(SafePodApplication.getHourOfDay(), 0) == 0)
            editor.putInt(SafePodApplication.getHourOfDay(), hour);
        if (sharedPreferences.getInt(SafePodApplication.getMinute(), 0) == 0)
            editor.putInt(SafePodApplication.getMinute(), minute);

        //		if(sharedPreferences.getString(SafepodAppApplication.getUserHomeAddress(), "empty") == "empty")
        //			editor.putString(SafepodAppApplication.getUserHomeAddress(), "Default Location");
        editor.commit();
    }

//
//    public void createShortCut(){
//        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
//        shortcutintent.putExtra("duplicate", false);
//        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
//        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher);
//        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
//        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext(), EnterActivity.class));
//        sendBroadcast(shortcutintent);
//    }
}