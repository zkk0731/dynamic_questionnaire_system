package com.example.dynamic_questionnaire_system.vo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.dynamic_questionnaire_system.entity.QuestionsAndAns;
import com.fasterxml.jackson.annotation.JsonFormat;

public class QuestionsReq {

	private int questionnaireId;
	
	private List<Integer> questionnaireIdList;
	
	private String title;
	
	private String description;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate startTime;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate endTime;
	
	private List<QuestionsAndAns> qaList;
	
	private List<Integer> QaIdList;
	
	private List<String> titleList;
	
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

	public List<Integer> getQuestionnaireIdList() {
		return questionnaireIdList;
	}

	public void setQuestionnaireIdList(List<Integer> questionnaireIdList) {
		this.questionnaireIdList = questionnaireIdList;
	}

	public List<Integer> getQaIdList() {
		return QaIdList;
	}

	public void setQaIdList(List<Integer> qaIdList) {
		QaIdList = qaIdList;
	}

	public List<String> getTitleList() {
		return titleList;
	}

	public void setTitleList(List<String> titleList) {
		this.titleList = titleList;
	}
	
	
	
}
