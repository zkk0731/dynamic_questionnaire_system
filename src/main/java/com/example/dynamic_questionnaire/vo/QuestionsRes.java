package com.example.dynamic_questionnaire.vo;

import java.util.List;
import java.util.Map;

import com.example.dynamic_questionnaire.entity.Questionnaire;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class QuestionsRes {

	private String message;
	
	private List<Questionnaire> questionnaireList;
	
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
	
	
}
