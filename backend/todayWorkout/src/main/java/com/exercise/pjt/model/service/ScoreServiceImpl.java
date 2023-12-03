package com.exercise.pjt.model.service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exercise.pjt.model.dao.ScoreDao;
import com.exercise.pjt.model.dto.ExerciseScore;
import com.exercise.pjt.model.dto.Score;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreDao scoreDao;

    @Override
    public void calculateScore(List<String> exerciseList) {
    }

    @Override
    public Map<String, Integer> getExerciseScores() {
    	List<ExerciseScore> exerciseScores = scoreDao.getExerciseScores();
        exerciseScores.sort(Comparator.comparingInt(ExerciseScore::getScore)); 
        Map<String, Integer> result = new LinkedHashMap<>(); // LinkedHashMap 쓰면 순서대로 가져올 수 있다!!!!!!!!
        for (ExerciseScore exerciseScore : exerciseScores) {
            result.put(exerciseScore.getName(), exerciseScore.getScore());
        }
        return result;
    }

    @Override
    public List<Score> getRanking() {
        return scoreDao.selectRanking();
    }

    @Override
    public List<Map<String, Object>> getRankingScores() {
        return scoreDao.selectRankingScores();
    }

    @Override
    public List<Score> getAllScores() {
        return scoreDao.selectAllScores();
    }
    
    @Override
    public List<String> getExerciseList() {
    	List<String> exerciseList = scoreDao.getExerciseList();

        // Log the exerciseList to check if it's being populated correctly
        System.out.println("Exercise List from Service: " + exerciseList);

        return exerciseList;
    }
    
    //Score update
  	@Transactional
  	@Override
  	public void updateScore(Score score) {
  		scoreDao.updateScore(score);
  		
  	}
}
