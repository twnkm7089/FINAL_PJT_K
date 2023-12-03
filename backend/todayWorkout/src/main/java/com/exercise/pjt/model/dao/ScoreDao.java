package com.exercise.pjt.model.dao;

import java.util.List;
import java.util.Map;

import com.exercise.pjt.model.dto.ExerciseScore;
import com.exercise.pjt.model.dto.Score;

public interface ScoreDao {   
    Score selectScore(String userId);
    
    void updateScore(Score score);
    
    void saveScore(Score score);
    
    void deleteScore(String userId);
    
    List<Score> selectRanking();
    
    List<Score> selectAllScores();
    
    List<Map<String, Object>> selectRankingScores();
    
    // 추가된 메서드
    List<ExerciseScore> getExerciseScores();
    
    List<String> getExerciseList();
    
}
