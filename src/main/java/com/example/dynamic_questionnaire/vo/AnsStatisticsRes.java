package com.example.dynamic_questionnaire.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AnsStatisticsRes {

	private String message;
	
	private List<QAndAStatistics> qaStatisticsList;

	public AnsStatisticsRes() {
		
	}
	
	public AnsStatisticsRes(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<QAndAStatistics> getQaStatisticsList() {
		return qaStatisticsList;
	}

	public void setQaStatisticsList(List<QAndAStatistics> qaStatisticsList) {
		this.qaStatisticsList = qaStatisticsList;
	}
	
	
	
}
