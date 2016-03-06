package org.safepodapp.android.beans;

import java.util.ArrayList;

public class ForumPost {
    private int id;
    private String body;
    private String[] tags = {};
    private int upvotes = 0;
    private int downvotes = 0;
    private ArrayList<Comment> comments;
    private String appSignature;

    public String getAppSignature() {
        return appSignature;
    }

    public void setAppSignature(String appSignature) {
        this.appSignature = appSignature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setTitle(String[] tags) {
        int counter = 0;
        for (String tag : tags)
            tags[counter++] = tag;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }
}