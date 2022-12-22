package com.example.dynamic_questionnaire.vo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.dynamic_questionnaire.entity.QuestionsAndAns;

public class QuestionsReq {

	private int questionnaireId;
	
	private String title;
	
	private String description;
	
	private LocalDate startTime;
	
	private LocalDate endTime;
	
	private List<QuestionsAndAns> qaList;
	
	private String questions;
	
	private String ans;
	
	private boolean oneOrMany;
	
	private boolean necessary;
	
	public QuestionsReq() {
		
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

	public String getQuestions() {
		return questions;
	}

	public void setQuestions(String questions) {
		this.questions = questions;
	}

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	public boolean isOneOrMany() {
		return oneOrMany;
	}

	public void setOneOrMany(boolean oneOrMany) {
		this.oneOrMany = oneOrMany;
	}

	public boolean isNecessary() {
		return necessary;
	}

	public void setNecessary(boolean necessary) {
		this.necessary = necessary;
	}

	public int getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(int questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public List<QuestionsAndAns> getQaList() {
		return qaList;
	}

	public void setQaList(List<QuestionsAndAns> qaList) {
		this.qaList = qaList;
	}
	
	
}
