package com.exercise.pjt.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exercise.pjt.model.dao.UserDao;
import com.exercise.pjt.model.dto.User;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	//회원가입 시 중복 아이디 제거
	@Override
	public List<User> getUserList() {
		return userDao.selectAll();
	}
	
	//회원 등록, User 객체 이용
	@Transactional
	@Override
	public void signup(User user) {
		userDao.insertUser(user);
	}

	
	//로그인
	//유저 정보를 DB로부터 받아와 id와 비밀번호가 일치하면 해당 User객체 반환
	//반대의 경우, null 반환
	@Override
	public User signin(String id, String password) {
		User info = userDao.selectOne(id);
		if(info != null && info.getPassword().equals(password)) {
			return info;
		}
		return null;
	}

	//User객체 이용한 update
	@Transactional
	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
		
	}

	//회원 탈퇴, id 기반
	@Override
	public void deleteUser(String id) {
		userDao.deleteUser(id);
	}

	//마이페이지를 위한 유저 정보 받아오기
	@Override
	public User myPage(String id) {
		return userDao.selectOne(id);
	}

	//로그인 시 오늘 점수 초기화
	@Override
	public void initScore(String id) {
		userDao.initScore(id);
		
	}

	@Override
	public void changePw(User user) {
		userDao.changePw(user);
		
	}

}
