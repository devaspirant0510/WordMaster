package com.example.wordmaster.model.recycler;

// 단어 리스트
public class DictionaryWordItem {
    String kor;
    String eng;
    String roomKey;

    public DictionaryWordItem(){

    }
    public DictionaryWordItem(String kor, String eng) {
        this.kor = kor;
        this.eng = eng;
    }

    public String getKor() {
        return kor;
    }

    public void setKor(String kor) {
        this.kor = kor;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(String roomKey) {
        this.roomKey = roomKey;
    }
}
