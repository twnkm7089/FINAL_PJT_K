package com.exercise.pjt.model.service;

import java.util.List;
import java.util.Map;

import com.exercise.pjt.model.dto.Score;

public interface ScoreService {
    void calculateScore(List<String> exerciseList);

    Map<String, Integer> getExerciseScores();

    List<Score> getRanking();

    List<Map<String, Object>> getRankingScores();

    List<Score> getAllScores();
    
    List<String> getExerciseList();
    
    // 점수 업데이트
    void updateScore(Score score);
}
