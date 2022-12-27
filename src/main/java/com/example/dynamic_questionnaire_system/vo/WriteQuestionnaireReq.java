package com.example.dynamic_questionnaire_system.vo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class WriteQuestionnaireReq {
	
	private String name;
	
	private String phone;
	
	private String email;
	
	private int age;
	
	private Map<String,String> userAns;
	
	private LocalDateTime finishTime;
	
	private String gender;
	
	private String questionnaireTitle;

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

	public Map<String, String> getUserAns() {
		return userAns;
	}

	public void setUserAns(Map<String, String> userAns) {
		this.userAns = userAns;
	}

	public LocalDateTime getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(LocalDateTime finishTime) {
		this.finishTime = finishTime;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getQuestionnaireTitle() {
		return questionnaireTitle;
	}

	public void setQuestionnaireTitle(String questionnaireTitle) {
		this.questionnaireTitle = questionnaireTitle;
	}
	
	
	
}
