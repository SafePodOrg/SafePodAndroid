package org.safepodapp.android;

/**
 * Created by Prajit on 3/5/2016.
 */
public class SafePodApplication {
    private static final String DEBUG_TAG = "SAFEPOD_APP_DEBUG_TAG";
    private static final String WEBSERVICE_URI = "http://www.example.com";

    private static final String SHARED_PREFERENCE = "SAFEPOD_APPS_SAHRED_PREFERENCE";

    public static String getDebugTag() {
        return DEBUG_TAG;
    }

    public static String getWebserviceUri() {
        return WEBSERVICE_URI;
    }

    public static String getSharedPreference() {
        return SHARED_PREFERENCE;
    }
}