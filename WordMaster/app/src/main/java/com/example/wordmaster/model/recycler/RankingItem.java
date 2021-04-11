package com.example.wordmaster.data.recycler;

import android.widget.ImageView;

public class RankingItem {
    String user;
    int ranking;
    String id;
    String message;
    int imageView;

    public RankingItem(String user, int ranking, String id, String message, int imageView) {
        this.user = user;
        this.ranking = ranking;
        this.id = id;
        this.message = message;
        this.imageView = imageView;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }
}
