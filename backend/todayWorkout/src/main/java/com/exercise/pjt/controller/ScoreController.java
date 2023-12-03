package com.exercise.pjt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.pjt.model.dto.Score;
import com.exercise.pjt.model.service.ScoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags="스코어 컨트롤러")
public class ScoreController {
    
    @Autowired
    private ScoreService scoreService;
    
    @GetMapping("/score/calculate")
    @ApiOperation(value="calculateScore", notes="완료한 운동 목록 받아와서 점수 계산하는 부분")
    public ResponseEntity<Void> calculateScore(List<String> exerciseList) {
        scoreService.calculateScore(exerciseList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/exercise/scores")
    @ApiOperation(value="getExerciseScores", notes="운동별 점수 가져오기")
    public ResponseEntity<Map<String, Integer>> getExerciseScores() {
        Map<String, Integer> exerciseScores = scoreService.getExerciseScores();
        return new ResponseEntity<>(exerciseScores, HttpStatus.OK);
    }

    @GetMapping("/ranking")
    @ApiOperation(value="getRankingScores", notes="랭킹 정보 가져오는 부분")
    public ResponseEntity<List<Map<String, Object>>> getRankingScores() {
        List<Map<String, Object>> ranking = scoreService.getRankingScores();
        return new ResponseEntity<>(ranking, HttpStatus.OK);
    }
    
    @GetMapping("/score/ranking")
    @ApiOperation(value="getRanking", notes="유저들의 점수 받아와서 등수대로 정렬하는 부분")
    public ResponseEntity<List<Score>> getRanking() {
        List<Score> ranking = scoreService.getRanking();
        return new ResponseEntity<>(ranking, HttpStatus.OK);
    }
    
    @GetMapping("/exercise-list")
    @ApiOperation(value="getExerciseList", notes="운동정보 가져오는 부분")
    public ResponseEntity<List<String>> getExerciseList() {
        List<String> exerciseList = scoreService.getExerciseList();

        // Log the exerciseList to check if it's being populated correctly
        System.out.println("Exercise List from Backend: " + exerciseList);

        return new ResponseEntity<>(exerciseList, HttpStatus.OK);
    }
    
   //점수 수정
  	@PutMapping("/updateScore/{userId}")
  	@ApiOperation(value="updateScore", notes="점수 업데이트해주기 위한 부분")
  	public ResponseEntity<Void> updateScore(@RequestBody Score score, @PathVariable String userId){
  		System.out.println(score);
  		score.setUserId(userId);
  		scoreService.updateScore(score);
  		return new ResponseEntity<Void>(HttpStatus.OK);
  	}
    
}
