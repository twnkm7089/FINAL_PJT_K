package com.exercise.pjt.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.pjt.model.dto.User;
import com.exercise.pjt.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api-user")
@Api(tags="유저 컨트롤러")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//로그인
	@PostMapping("login")
	@ApiOperation(value="login", notes="로그인을 위한 메서드입니다.")
	public ResponseEntity<?> login(@RequestBody User user, HttpSession session){
		String id = user.getId();
		String password = user.getPassword();
		User info = userService.signin(id, password);
		//로그인 정보 없으면 반환
		if(info == null) {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
		
		//있으면 오늘 점수와 랭킹 초기화 후 반환
		userService.initScore(id);

		return new ResponseEntity<User> (info, HttpStatus.OK);
	}
	
	//로그아웃
	@GetMapping("logout")
	@ApiOperation(value="logout", notes="로그아웃을 위한 메서드입니다.")
	public ResponseEntity<Void> logout(HttpSession session){
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	
	//회원가입 시 중복 방지를 위한 연산
	@GetMapping("list/{id}")
	@ApiOperation(value="list", notes="아이디 중복 방지를 위해 DB에서 불러와 체크")
	public ResponseEntity<Boolean> list(@PathVariable String id){
		List<User> userList = userService.getUserList();
		boolean flag = true;
		for(int i = 0; i < userList.size(); i++) {
			if(id.equals(userList.get(i).getId())){
				flag = false;
				break;
			}
		}
		
		return new ResponseEntity<Boolean>(flag,HttpStatus.OK);
	}
	
	//회원가입
	@PostMapping("user")
	@ApiOperation(value="regist", notes="회원가입을 위한 메서드입니다.")
	public ResponseEntity<Void> regist(@RequestBody User user){
		//회원가입
		userService.signup(user);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	//회원정보 수정
	@PutMapping("user/{id}")
	@ApiOperation(value="modify", notes="회원정보 수정을 위한 메서드입니다.")
	public ResponseEntity<Void> modify(@RequestBody User user, @PathVariable String id){
		user.setId(id);
		userService.updateUser(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	//회원정보 삭제
	@DeleteMapping("user/{id}")
	@ApiOperation(value="delete", notes="회원 탈퇴를 위한 메서드입니다.")
	public ResponseEntity<Void> delete(@PathVariable String id){
		userService.deleteUser(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	//마이페이지 접근
	@GetMapping("user/{id}")
	@ApiOperation(value="mypage", notes="마이페이지 접속 시 마이페이지에 띄울 정보를 받아오기 위한 메서드입니다.")
	public ResponseEntity<?> myPage(@PathVariable String id){
		User info = userService.myPage(id);
		if(info == null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<User> (info, HttpStatus.OK);
	}
	
	//비밀번호 변경
	@PutMapping("user-pw")
	@ApiOperation(value="changePw", notes="비밀번호 변경을 위한 메서드입니다.")
	public ResponseEntity<Void> changePw(@RequestBody User user){
		userService.changePw(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
