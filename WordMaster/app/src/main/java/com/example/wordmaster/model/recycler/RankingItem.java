package com.example.wordmaster.model.recycler;

public class RankingItem {
    String user;
    int ranking;
    String id;
    String message;
    String imageView;

    public RankingItem(String user, int ranking, String id, String message, String imageView) {
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

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }
}
