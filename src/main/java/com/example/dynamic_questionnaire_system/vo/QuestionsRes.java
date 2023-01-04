package com.example.dynamic_questionnaire_system.vo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.dynamic_questionnaire_system.entity.Questionnaire;
import com.example.dynamic_questionnaire_system.entity.QuestionsAndAns;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class QuestionsRes {

	private String message;
	
	private String title;
	
	private String description;
	
	private LocalDate startTime;
	
	private LocalDate endTime;
	
	private List<Questionnaire> questionnaireList;
	
	private List<QuestionsAndAns> qaList;
	
	public QuestionsRes() {
		
	}
	
	public QuestionsRes(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Questionnaire> getQuestionnaireList() {
		return questionnaireList;
	}

	public void setQuestionnaireList(List<Questionnaire> questionnaireList) {
		this.questionnaireList = questionnaireList;
	}

	public List<QuestionsAndAns> getQaList() {
		return qaList;
	}

	public void setQaList(List<QuestionsAndAns> qaList) {
		this.qaList = qaList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDate startTime) {
		this.startTime = startTime;
	}

	public LocalDate getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDate endTime) {
		this.endTime = endTime;
	}
	
	
	
}
