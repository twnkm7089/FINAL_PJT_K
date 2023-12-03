package com.exercise.pjt.model.dao;

import java.util.List;

import com.exercise.pjt.model.dto.User;

public interface UserDao {
	
	//로그인, 마이페이지에 사용할 id기반 유저 정보 받아오기
	User selectOne(String id);
	
	//회원가입 시 중복 아이디 체크 위한 정보
	List<User> selectAll();
	
	//회원가입에 사용, DB에 유저 넣기
	void insertUser(User user);
	
	//회원정보 수정에 사용
	void updateUser(User user);

	//회원탈퇴에 사용, DB에서 유저 삭제
	void deleteUser(String id);

	//점수 초기화에 사용, DB에서 로그인한 유저 정보 초기화.
	void initScore(String id);
	
	//비밀번호 변경에 사용
	void changePw(User user);
	
}
