package com.exercise.pjt.model.dto;

public class ExerciseScore {
    private String name;
    private int score;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    @Override
    public String toString() {
        return "ExerciseScore [name=" + name + ", score=" + score + "]";
    }
}
