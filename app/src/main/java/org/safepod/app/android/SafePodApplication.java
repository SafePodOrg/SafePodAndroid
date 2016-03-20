package org.safepod.app.android;

/**
 * Created by Prajit on 3/5/2016.
 * The following are all Get calls and the input is in the URL:
 * Get top posts: http://safepodapp.org/forum/?sign=appSignKey&userid=deviceId
 * Get my posts: http://safepodapp.org/forum/my/?sign=appSignKey&userid=deviceId
 * Get queried topicwise posts: http://safepodapp.org/forum/search/?sign=appSignKey&userid=deviceId&q=searchQuery
 * Get specific post detail: http://safepodapp.org/forum/post/?sign=appSignKey&userid=deviceId&id=postId
 * <p/>
 * The following comments are Post calls that will be accompanied by JSON:
 * Make new post: http://safepodapp.org/forum/post/new/ (input: )
 * Make new comment: http://safepodapp.org/forum/post/comment/new/ (input: )
 * Click support button: http://safepodapp.org/forum/post/feedback (input: )
 * Click comment flag button: http://safepodapp.org/forum/post/comment/feedback (input: )
 */
public class SafePodApplication {
    private static final String DEBUG_TAG = "SAFEPOD_APP_DEBUG_TAG";
    private static final String ERROR_TAG = "No data found for: ";
    private static final String BASE_URI = "http://safepodapp.org/forum";
    private static final String URI_SLASH = "/";
    private static final String URI_QUESTION_MARK = "?";
    private static final String URI_AMPERSAND = "&";
    private static final String URI_SEARCH = "search";
    private static final String URI_POST = "post";
    private static final String URI_MY = "my";
    private static final String URI_VARIABLE_APP_SIGNATURE = "sign=";
    private static final String URI_VARIABLE_APP_SIGNATURE_KEY = "sign";
    private static final String URI_VARIABLE_DEVICE_IDENTIFIER = "userId=";
    private static final String URI_VARIABLE_DEVICE_IDENTIFIER_KEY = "userId";
    private static final String URI_VARIABLE_SEARCH_QUERY_TEXT = "q=";
    private static final String URI_VARIABLE_SEARCH_QUERY_TEXT_KEY = "q";
    private static final String URI_VARIABLE_POST_IDENTIFIER = "id=";
    private static final String URI_VARIABLE_POST_IDENTIFIER_KEY = "id";
    private static final String SHARED_PREFERENCE = "SAFEPOD_APPS_SAHRED_PREFERENCE";

    public static String getDebugTag() {
        return DEBUG_TAG;
    }

    public static String getErrorTag() {
        return ERROR_TAG;
    }

    public static String getBaseUri() {
        return BASE_URI;
    }

    public static String getUriSlash() {
        return URI_SLASH;
    }

    public static String getUriQuestionMark() {
        return URI_QUESTION_MARK;
    }

    public static String getUriAmpersand() {
        return URI_AMPERSAND;
    }

    public static String getUriSearch() {
        return URI_SEARCH;
    }

    public static String getUriPost() {
        return URI_POST;
    }

    public static String getUriMy() {
        return URI_MY;
    }

    public static String getUriVariableAppSignature() {
        return URI_VARIABLE_APP_SIGNATURE;
    }

    public static String getUriVariableAppSignatureKey() {
        return URI_VARIABLE_APP_SIGNATURE_KEY;
    }

    public static String getUriVariableDeviceIdentifier() {
        return URI_VARIABLE_DEVICE_IDENTIFIER;
    }

    public static String getUriVariableDeviceIdentifierKey() {
        return URI_VARIABLE_DEVICE_IDENTIFIER_KEY;
    }

    public static String getUriVariableSearchQueryText() {
        return URI_VARIABLE_SEARCH_QUERY_TEXT;
    }

    public static String getUriVariableSearchQueryTextKey() {
        return URI_VARIABLE_SEARCH_QUERY_TEXT_KEY;
    }

    public static String getUriVariablePostIdentifier() {
        return URI_VARIABLE_POST_IDENTIFIER;
    }

    public static String getUriVariablePostIdentifierKey() {
        return URI_VARIABLE_POST_IDENTIFIER_KEY;
    }

    public static String getSharedPreference() {
        return SHARED_PREFERENCE;
    }
}