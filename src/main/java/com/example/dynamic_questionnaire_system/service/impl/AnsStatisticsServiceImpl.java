package com.example.dynamic_questionnaire_system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dynamic_questionnaire_system.constants.RtnCode;
import com.example.dynamic_questionnaire_system.entity.QuestionsAndAns;
import com.example.dynamic_questionnaire_system.entity.Users;
import com.example.dynamic_questionnaire_system.repository.QuestionsAndAnsDao;
import com.example.dynamic_questionnaire_system.repository.UsersDao;
import com.example.dynamic_questionnaire_system.service.ifs.AnsStatisticsService;
import com.example.dynamic_questionnaire_system.vo.AnsStatisticsReq;
import com.example.dynamic_questionnaire_system.vo.AnsStatisticsRes;
import com.example.dynamic_questionnaire_system.vo.QAndAStatistics;

@Service
public class AnsStatisticsServiceImpl implements AnsStatisticsService{

	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private QuestionsAndAnsDao questionsAndAnsDao;
	
	@Override
	public AnsStatisticsRes statistics(AnsStatisticsReq req) {
//		List<QuestionsAndAns> qaList = questionsAndAnsDao.findByQuestionnaireTitle(req.getQuestionnaireTitle());
		List<Users> users = usersDao.findByQuestionnaireTitle(req.getQuestionnaireTitle());
		
		Map<String,Map<String,Integer>> staMap = new HashMap<>();
		
		List<String> usersAns = new ArrayList<>();
		for(Users item : users) {
			usersAns.add(item.getUserAns());
		}
		Map<String, Map<String, Integer>> result = statisticsFunction(usersAns);
		
		AnsStatisticsRes res = new AnsStatisticsRes();
		res.setQaStatisticsMap(result);
		res.setMessage(RtnCode.SUCCESS.getMessage());
		return res;
	}

	private Map<String,Map<String,Integer>> statisticsFunction(List<String> usersAns) {
//		List<QAndAStatistics> qaStatisticsList = new ArrayList<>();
//		QAndAStatistics qaStatistics = new QAndAStatistics();
//		List<String> qaList = new ArrayList<>();
		Map<String,Map<String,Integer>> qaStatisticsMap = new HashMap<>();
//		Map<String,Integer> ansStatisticsMap = new HashMap<>();
		for(String item : usersAns) {
			
			for(String item2 : item.split(",")) {
				 String question = item2.split("=")[0].trim();
				 String ans = item2.split("=")[1].trim();
				 
				 //多選
				 if(ans.contains(";")) {
					 String[] ansArray = ans.split(";");
					 
					 for(String answers : ansArray) {
						 mapPutStatistics(qaStatisticsMap,answers,question);
					 }
				 }else {
					 mapPutStatistics(qaStatisticsMap,ans,question);
				 }
			}
		}
		return qaStatisticsMap;
	}

	private Map<String,Map<String,Integer>> mapPutStatistics(Map<String,Map<String,Integer>> qaStatisticsMap,
			String ans, String question){
		Map<String,Integer> ansStatisticsMap = new HashMap<>();
		 
		 if(qaStatisticsMap.containsKey(question)) {
			 Map<String, Integer> containMap = qaStatisticsMap.get(question);
			 
			 if(containMap.containsKey(ans)) {
				 int count1 =  containMap.get(ans);
				 containMap.put(ans, count1 + 1);
			 }else {
				 containMap.put(ans, 1);
			 }
			 qaStatisticsMap.put(question, containMap);
		 }else {
			 ansStatisticsMap.put(ans, 1);
			 qaStatisticsMap.put(question, ansStatisticsMap);
		 }
		return qaStatisticsMap;
	}
	
}
