package com.example.dynamic_questionnaire_system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "questions_and_ans")
public class QuestionsAndAns {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "questionnaire_title")
	private String questionnaireTitle;
	
	@Column(name = "questions")
	private String questions;
	
	@Column(name = "ans")
	private String ans;
	
	@Column(name = "one_or_many")
	private boolean oneOrMany;
	
	@Column(name = "necessary")
	private boolean necessary;

	public QuestionsAndAns() {
		
	}
	
	public QuestionsAndAns(String questions, String ans, boolean oneOrMany, boolean necessary) {
		
		this.questions = questions;
		this.ans = ans;
		this.oneOrMany = oneOrMany;
		this.necessary = necessary;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestionnaireTitle() {
		return questionnaireTitle;
	}

	public void setQuestionnaireTitle(String questionnaireTitle) {
		this.questionnaireTitle = questionnaireTitle;
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
	
	
}
