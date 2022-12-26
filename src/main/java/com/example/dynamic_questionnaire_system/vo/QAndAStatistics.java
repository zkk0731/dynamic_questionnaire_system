package com.example.dynamic_questionnaire_system.vo;

import java.util.Map;

public class QAndAStatistics {
	private String question;
	
	private Map<String, Integer> ansStatistics;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Map<String, Integer> getAnsStatistics() {
		return ansStatistics;
	}

	public void setAnsStatistics(Map<String, Integer> ansStatistics) {
		this.ansStatistics = ansStatistics;
	}
	
	
}
