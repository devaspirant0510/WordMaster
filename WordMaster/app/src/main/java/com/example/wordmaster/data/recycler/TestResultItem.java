package com.example.wordmaster.data.recycler;

public class TestResultItem {
    int index;
    String question;
    String answer;
    String myAnswer;
    String isCorrect;

    public TestResultItem(int index,String question, String answer, String myAnswer, String isCorrect) {
        this.index = index;
        this.question = question;
        this.answer = answer;
        this.myAnswer = myAnswer;
        this.isCorrect = isCorrect;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }

    public String isCorrect() {
        return isCorrect;
    }

    public void setCorrect(String correct) {
        isCorrect = correct;
    }
}
