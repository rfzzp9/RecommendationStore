package com.example.calltaxiapp;

import android.widget.ImageView;

import java.io.Serializable;

public class post_comments_scroll_data implements Serializable {

    private int imageview_anonymous_image;
    private String textview_nickname;
    private String textview_content;
    private String textview_time;

    public post_comments_scroll_data(int imageview_anonymous_image, String textview_nickname, String textview_content, String textview_time) {
        this.imageview_anonymous_image = imageview_anonymous_image;
        this.textview_nickname = textview_nickname;
        this.textview_content = textview_content;
        this.textview_time = textview_time;
    }

    public int getImageview_anonymous_image() {
        return imageview_anonymous_image;
    }

    public void setImageview_anonymous_image(int imageview_anonymous_image) {
        this.imageview_anonymous_image = imageview_anonymous_image;
    }

    public String getTextview_nickname() {
        return textview_nickname;
    }

    public void setTextview_nickname(String textview_nickname) {
        this.textview_nickname = textview_nickname;
    }

    public String getTextview_content() {
        return textview_content;
    }

    public void setTextview_content(String textview_content) {
        this.textview_content = textview_content;
    }

    public String getTextview_time() {
        return textview_time;
    }

    public void setTextview_time(String textview_time) {
        this.textview_time = textview_time;
    }
}
