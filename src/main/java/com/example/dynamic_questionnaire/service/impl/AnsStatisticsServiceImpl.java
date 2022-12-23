package com.example.dynamic_questionnaire.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dynamic_questionnaire.entity.QuestionsAndAns;
import com.example.dynamic_questionnaire.entity.Users;
import com.example.dynamic_questionnaire.repository.QuestionsAndAnsDao;
import com.example.dynamic_questionnaire.repository.UsersDao;
import com.example.dynamic_questionnaire.service.ifs.AnsStatisticsService;
import com.example.dynamic_questionnaire.vo.AnsStatisticsReq;
import com.example.dynamic_questionnaire.vo.AnsStatisticsRes;
import com.example.dynamic_questionnaire.vo.QAndAStatistics;

@Service
public class AnsStatisticsServiceImpl implements AnsStatisticsService{

	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private QuestionsAndAnsDao questionsAndAnsDao;
	
	@Override
	public AnsStatisticsRes statistics(AnsStatisticsReq req) {
		List<QuestionsAndAns> qaList = questionsAndAnsDao.findByQuestionnaireTitle(req.getQuestionnaireTitle());
		List<Users> users = usersDao.findByQuestionnaireTitle(req.getQuestionnaireTitle());
		
		List<String> usersAns = new ArrayList<>();
		for(Users item : users) {
			usersAns.add(item.getUserAns());
		}
		
		
		return null;
	}

	private void ansStr(List<String> usersAns) {
		List<QAndAStatistics> qaStatisticsList = new ArrayList<>();
		QAndAStatistics qaStatistics = new QAndAStatistics();
		List<String> qaList = new ArrayList<>();
		Map<String,Integer> qaStatisticsMap = new HashMap<>();
		for(String item : usersAns) {
			
			for(String item2 : item.split(",")) {
				 String question = item2.split("=")[0];
				 String ans = item2.split("=")[1];
				 
				 if(qaStatisticsMap.containsKey(ans)) {
					 int count = qaStatisticsMap.get(ans);
					 qaStatisticsMap.put(ans, count + 1);
				 }else {
					 qaStatisticsMap.put(ans, 1);
				 }
				 
			}
		}
		
	}
	
}
