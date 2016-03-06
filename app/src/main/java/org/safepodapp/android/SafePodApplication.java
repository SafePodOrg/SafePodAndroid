package org.safepodapp.android;

/**
 * Created by Prajit on 3/5/2016.
 */
public class SafePodApplication {
    private static final String DEBUG_TAG = "SAFEPOD_APP_DEBUG_TAG";
    private static final String WEBSERVICE_URI = "http://www.example.com";

//    private static final String SHARED_PREFERENCE = "SAFEPOD_APPS_SAHRED_PREFERENCE";
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY_OF_MONTH = "dayOfMonth";
    private static final String HOUR_OF_DAY = "hourOfDay";
    private static final String MINUTE = "minute";

    public static String getDebugTag() {
        return DEBUG_TAG;
    }
    public static String getWebserviceUri() {
        return WEBSERVICE_URI;
    }
//    public static String getSharedPreference() {
//        return SHARED_PREFERENCE;
//    }
    public static String getYear() {
        return YEAR;
    }
    public static String getMonth() {
        return MONTH;
    }
    public static String getDayOfMonth() {
        return DAY_OF_MONTH;
    }
    public static String getHourOfDay() {
        return HOUR_OF_DAY;
    }
    public static String getMinute() {
        return MINUTE;
    }
}