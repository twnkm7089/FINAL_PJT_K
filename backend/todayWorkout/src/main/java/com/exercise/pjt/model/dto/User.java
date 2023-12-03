package com.exercise.pjt.model.dto;
//User DTO
//DB의 User table과 필드 및 변수 종류를 일치시켰음.
public class User {
	private String id;
	private String nickname;
	private String password;
	private String email;
	private String introduction;
	
	public User() {}

	public User(String id, String nickname, String password, String email, String introduction) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.introduction = introduction;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", nickname=" + nickname + ", password=" + password + ", email=" + email
				+ ", introduction=" + introduction + "]";
	};
	
	
}
