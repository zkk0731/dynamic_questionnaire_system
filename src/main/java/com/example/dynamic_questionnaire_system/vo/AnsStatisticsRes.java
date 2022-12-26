package com.example.dynamic_questionnaire_system.vo;

import java.util.List;
import java.util.Map;

import com.example.dynamic_questionnaire_system.entity.Users;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AnsStatisticsRes {

	private String message;
	
	private List<QAndAStatistics> qaStatisticsList;
	
	private Map<String,Map<String,Integer>>qaStatisticsMap;
	
	private List<Users> usersList;
	
	private Users user;

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

	public List<Users> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<Users> usersList) {
		this.usersList = usersList;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
}
