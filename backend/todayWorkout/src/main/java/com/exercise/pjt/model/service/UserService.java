package com.exercise.pjt.model.service;

import java.util.List;

import com.exercise.pjt.model.dto.User;

public interface UserService {
	//회원가입시 중복 아이디 방지 위해
	List<User> getUserList();
	//회원 가입
	void signup(User user);
	//로그인
	User signin(String id, String password);
	//회원 정보 수정
	void updateUser(User user);
	//회원 탈퇴
	void deleteUser(String id);
	//마이페이지를 위한 정보 출력
	User myPage(String id);
	
	//로그인 시 오늘 점수 초기화 함수
	void initScore(String id);
	
	//비밀번호 변경
	void changePw(User user);
	
}
