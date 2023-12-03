package com.exercise.pjt.model.dto;

public class Score {
    private int id;
    private String userId;
    private int totalScore;
    private int todayScore;
    private String lastRegDate;
    
    public Score() {}

    //id는 DB에서 자동 생성되므로 이를 제외한 나머지만 넣어도 생성
    public Score(String userId, int totalScore, int todayScore, String lastRegDate) {
        super();
        this.userId = userId;
        this.totalScore = totalScore;
        this.todayScore = todayScore;
        this.lastRegDate = lastRegDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTodayScore() {
        return todayScore;
    }

    public void setTodayScore(int todayScore) {
        this.todayScore = todayScore;
    }

    public String getLastRegDate() {
        return lastRegDate;
    }

    public void setLastRegDate(String lastRegDate) {
        this.lastRegDate = lastRegDate;
    }

    @Override
    public String toString() {
        return "Score [id=" + id + ", userId=" + userId + ", totalScore=" + totalScore + ", todayScore=" + todayScore
                + ", lastRegDate=" + lastRegDate + "]";
    };
    
    
    
    
}