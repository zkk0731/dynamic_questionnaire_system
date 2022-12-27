package com.example.dynamic_questionnaire_system.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "age")
	private int age;
	
	@Column(name = "user_ans")
	private String userAns;
	
	@Column(name = "finish_time")
	private LocalDateTime finishTime;
	
	@Column(name = "questionnaire_title")
	private String questionnaireTitle;

	@Column(name = "gender")
	private String gender;
	
	public Users() {
		
	}
	
	public Users(String name, String phone, String email, int age, String userAns
			, LocalDateTime finishTime, String questionnaireTitle, String gender) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.userAns = userAns;
		this.finishTime = finishTime;
		this.questionnaireTitle = questionnaireTitle;
		this.gender = gender;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getUserAns() {
		return userAns;
	}

	public void setUserAns(String userAns) {
		this.userAns = userAns;
	}

	public LocalDateTime getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(LocalDateTime finishTime) {
		this.finishTime = finishTime;
	}

	public String getQuestionnaireTitle() {
		return questionnaireTitle;
	}

	public void setQuestionnaireTitle(String questionnaireTitle) {
		this.questionnaireTitle = questionnaireTitle;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
}
