package com.example.dynamic_questionnaire_system.vo;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AnsStatisticsRes {

	private String message;
	
	private List<QAndAStatistics> qaStatisticsList;
	
	private Map<String,Map<String,Integer>>qaStatisticsMap;

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

	public Map<String, Map<String, Integer>> getQaStatisticsMap() {
		return qaStatisticsMap;
	}

	public void setQaStatisticsMap(Map<String, Map<String, Integer>> qaStatisticsMap) {
		this.qaStatisticsMap = qaStatisticsMap;
	}
	
	
	
}
