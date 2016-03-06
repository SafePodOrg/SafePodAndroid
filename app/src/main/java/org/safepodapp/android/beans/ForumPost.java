package org.safepodapp.android.beans;

import org.json.JSONArray;

import java.util.ArrayList;

public class ForumPost {
    private String id;
    private String body;
    private String[] tags = {};
    private String upvotes;
    private String downvotes;
    private ArrayList<Comment> comments;
    private String appSignature;

    public String getAppSignature() {
        return appSignature;
    }

    public void setAppSignature(String appSignature) {
        this.appSignature = appSignature;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTags() {
        StringBuffer tempTagList = new StringBuffer();
        for (String tag : tags) {
            tempTagList.append(tag);
            tempTagList.append(" ");
        }
        return tempTagList.toString();
    }

    public void setTags(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            try {
                tags[i] = array.getJSONObject(i).toString();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(JSONArray array) {

    }

    public void setTitle(String[] tags) {
        int counter = 0;
        for (String tag : tags)
            tags[counter++] = tag;
    }

    public String getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(String upvotes) {
        this.upvotes = upvotes;
    }

    public String getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(String downvotes) {
        this.downvotes = downvotes;
    }
}