package com.example.calltaxiapp;

import java.io.Serializable;

public class bulletinboard_scroll_data implements Serializable {
    private String title_text;
    private String content_text;
    private String time_text;
    private String nickname_text;
    private String count_likeicon_text;
    private String count_commenticon_text;

    public bulletinboard_scroll_data(String title_text, String content_text, String time_text, String nickname_text, String count_likeicon_text, String count_commenticon_text) {
        this.title_text = title_text;
        this.content_text = content_text;
        this.time_text = time_text;
        this.nickname_text = nickname_text;
        this.count_likeicon_text = count_likeicon_text;
        this.count_commenticon_text = count_commenticon_text;
    }

    public String getTitle_text() {
        return title_text;
    }

    public void setTitle_text(String title_text) {
        this.title_text = title_text;
    }

    public String getContent_text() {
        return content_text;
    }

    public void setContent_text(String content_text) {
        this.content_text = content_text;
    }

    public String getTime_text() {
        return time_text;
    }

    public void setTime_text(String time_text) {
        this.time_text = time_text;
    }

    public String getNickname_text() {
        return nickname_text;
    }

    public void setNickname_text(String nickname_text) {
        this.nickname_text = nickname_text;
    }

    public String getCount_likeicon_text() {
        return count_likeicon_text;
    }

    public void setCount_likeicon_text(String count_likeicon_text) {
        this.count_likeicon_text = count_likeicon_text;
    }

    public String getCount_commenticon_text() {
        return count_commenticon_text;
    }

    public void setCount_commenticon_text(String count_commenticon_text) {
        this.count_commenticon_text = count_commenticon_text;
    }

}
