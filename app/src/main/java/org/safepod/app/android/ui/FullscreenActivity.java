package org.safepod.app.android.ui;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import org.safepod.app.android.R;

import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String myPref = "MyPrefForApp";
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;
    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    public static SharedPreferences sharedPreferences;
    private static boolean idCreated = false;
    private static boolean orbotInstalled = false;
    private static boolean orbotActive = false;
    private static int Id;
    //    public
    private final Handler mHideHandler = new Handler();
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };
    public Button installButton;
    String orb = "org.torproject.android";
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;

    private void setDefaultSharedPreferences() {
        sharedPreferences = getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        final Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//        int hour = 23; //Default expectation is you will reach home at 11
//        int minute = 0;
//
//        if (sharedPreferences.getInt(SafePodApplication.getYear(), 1970) == 1970)
//            editor.putInt(SafePodApplication.getYear(), year);
//        if (sharedPreferences.getInt(SafePodApplication.getMonth(), 1) == 1)
//            editor.putInt(SafePodApplication.getMonth(), month);
//        if (sharedPreferences.getInt(SafePodApplication.getDayOfMonth(), 1) == 1)
//            editor.putInt(SafePodApplication.getDayOfMonth(), day);
//        if (sharedPreferences.getInt(SafePodApplication.getHourOfDay(), 0) == 0)
//            editor.putInt(SafePodApplication.getHourOfDay(), hour);
//        if (sharedPreferences.getInt(SafePodApplication.getMinute(), 0) == 0)
//            editor.putInt(SafePodApplication.getMinute(), minute);
//
//        		if(sharedPreferences.getString(SafepodAppApplication.getUserHomeAddress(), "empty") == "empty")
//        			editor.putString(SafepodAppApplication.getUserHomeAddress(), "Default Location");
        editor.putInt("devId", Id);
        if (idCreated)
            editor.putInt("IdSet", 1);
        editor.commit();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.dummy_button:
                if (orbotInstalled) {
                    Intent goToPlayStore = new Intent(Intent.ACTION_VIEW);
                    goToPlayStore.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + orb));
//                goToPlayStore.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    startActivity(goToPlayStore);
                } else if (orbotActive) {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(orb);
                    startActivity(launchIntent);
                } else if (!idCreated || sharedPreferences.getInt("IdSet", 0) != 1) {
                    long id = new java.util.Random(1000000).nextLong();
//                    SharedPreferences sharedPreferences
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Id = (int) id;
                    idCreated = true;
                    setDefaultSharedPreferences();
                    Intent getRand = new Intent(getApplicationContext(), MainActivity.class);

                    idCreated = true;
                    getRand.putExtra("RandVal", id);
                    getRand.putExtra("IdSet", 1);

                    startActivity(getRand);
                }

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
//        installButton = (Button) findViewById(R.id.InstallButton);
//        installButton.setOnClickListener(this);
//        installButton.setVisibility(View.GONE);
        findViewById(R.id.dummy_button).setOnClickListener(this);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    public boolean checkIfOrbIsRunning() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        for (int i = 0; i < procInfos.size(); i++) {
            if (procInfos.get(i).processName.equals(orb)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfOrbStarted() {
//        try {
////            NetworkServices.getProxyConnection(new URL("http://safepodapp.org/"));
//            Intent in = new Intent(getApplicationContext(), testOrb.class);
//            startActivity(in);
//
//            if (x.anyException)
//                return false;
//            else
//                return  true;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
        return true;
//        }
//        return true;
    }

    //    class testOrb extends AppCompatActivity {
//        Boolean anyException = Boolean.TRUE;
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
////            super.onCrea
//            try {
//                NetworkServices.sendGet("http://safepodapp.org/");
//                anyException = Boolean.FALSE;
//            } catch (Exception e) {
//                e.printStackTrace();
//                anyException = Boolean.TRUE;
//            }
//
//        }
//    }
    @Override
    public void onResume() {
        super.onResume();
        setDefaultSharedPreferences();
        findViewById(R.id.dummy_button).setVisibility(View.GONE);
//        toggle();
//        installButton.setVisibility(View.GONE);
        Button b = (Button) findViewById(R.id.dummy_button);
//        idCreated = false;
        orbotActive = false;
        orbotInstalled = false;
        if (!checkIfAppInstalled(orb, getApplicationContext())) {
//                installButton.setVisibility(View.VISIBLE);
            findViewById(R.id.dummy_button).setVisibility(View.VISIBLE);
            orbotInstalled = true;

            b.setText("Install Orbot");
        } else if (!checkIfOrbStarted()) {
            findViewById(R.id.dummy_button).setVisibility(View.VISIBLE);
            b.setText("Start the Orbot App");
            orbotActive = true;
        } else if (sharedPreferences.getInt("IdSet", 0) != 1) {

            findViewById(R.id.dummy_button).setVisibility(View.VISIBLE);
            b.setText("Create Initial ID");
            idCreated = true;
        } else {
            Intent getRand = new Intent(getApplicationContext(), MainActivity.class);

            idCreated = true;
            getRand.putExtra("RandVal", sharedPreferences.getInt("devId", 0));
            getRand.putExtra("IdSet", sharedPreferences.getInt("IdSet", 0));
            startActivity(getRand);
        }

    }

    private Boolean checkIfAppInstalled(String uri, Context context) {
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
//        mControlsView.setVisibility(View.GONE);
//        mVisible = false;
//
//        // Schedule a runnable to remove the status and navigation bar after a delay
//        mHideHandler.removeCallbacks(mShowPart2Runnable);
//        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
