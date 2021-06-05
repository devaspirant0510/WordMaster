package com.example.wordmaster.model.firebase;

import com.example.wordmaster.model.recycler.DictionaryWordItem;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class UserDictionary {
    String option;
    String title;
    int maxCount;
    int currentCount;
    String description;
    String hashTag;
    DictionaryWordItem wordItem;
    String host;
    ArrayList<String> contributor;
    String password;
    String roomKey;
    String hostId;
    public UserDictionary(){

    }

    /**
     * 생성자
     * @param option 단어 공개여부
     * @param title 단어 제목
     * @param maxCount 단어 설정한 개수
     * @param currentCount 현재 입렫된 단어의 개수
     * @param description 단어 설명
     * @param hashTag 해시태그
     * @param wordItem 단어저장
     * @param host 단어 게시자 이름
     * @param contributor 수정권한 유저
     * @param password 비밀번호(private) 일경우
     * @param roomKey 해당 단어장 room Key
     * @param hostId 게시자의 ID
     */
    public void init(String option, String title, int maxCount, int currentCount,
                     String description, String hashTag, DictionaryWordItem wordItem,
                     String host, ArrayList<String> contributor, String password,
                     String roomKey, String hostId) {
        this.option = option;
        this.title = title;
        this.maxCount = maxCount;
        this.currentCount = currentCount;
        this.description = description;
        this.hashTag = hashTag;
        this.wordItem = wordItem;
        this.host = host;
        this.contributor = contributor;
        this.password = password;
        this.roomKey = roomKey;
        this.hostId = hostId;
    }
    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public DictionaryWordItem getWordItem() {
        return wordItem;
    }

    public void setWordItem(DictionaryWordItem wordItem) {
        this.wordItem = wordItem;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public ArrayList<String> getContributor() {
        return contributor;
    }

    public void setContributor(ArrayList<String> contributor) {
        this.contributor = contributor;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(String roomKey) {
        this.roomKey = roomKey;
    }
}
