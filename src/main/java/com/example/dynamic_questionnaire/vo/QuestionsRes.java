package com.example.dynamic_questionnaire.vo;

import java.util.List;
import java.util.Map;

import com.example.dynamic_questionnaire.entity.Questionnaire;
import com.example.dynamic_questionnaire.entity.QuestionsAndAns;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class QuestionsRes {

	private String message;
	
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
	
	
	
}
