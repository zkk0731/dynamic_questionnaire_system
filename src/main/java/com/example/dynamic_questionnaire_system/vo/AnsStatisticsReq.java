package com.example.dynamic_questionnaire_system.vo;

public class AnsStatisticsReq {

	private String questionnaireTitle;
	
	private int questionnaireId;
	
	private int usersId;

	public String getQuestionnaireTitle() {
		return questionnaireTitle;
	}

	public void setQuestionnaireTitle(String questionnaireTitle) {
		this.questionnaireTitle = questionnaireTitle;
	}

	public int getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(int questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public int getUsersId() {
		return usersId;
	}

	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}
	
	
}
