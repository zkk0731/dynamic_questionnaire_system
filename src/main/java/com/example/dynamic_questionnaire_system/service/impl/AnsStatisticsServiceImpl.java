package com.example.dynamic_questionnaire_system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dynamic_questionnaire_system.constants.RtnCode;
import com.example.dynamic_questionnaire_system.entity.Questionnaire;
import com.example.dynamic_questionnaire_system.entity.QuestionsAndAns;
import com.example.dynamic_questionnaire_system.entity.Users;
import com.example.dynamic_questionnaire_system.repository.QuestionnaireDao;
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
	
	@Autowired
	private QuestionnaireDao questionnaireDao;
	
	//統計答案
	@Override
	public AnsStatisticsRes statistics(AnsStatisticsReq req) {
		Optional<Questionnaire> questionnaire = questionnaireDao.findById(req.getQuestionnaireId());
		if(!questionnaire.isPresent()) {
			return new AnsStatisticsRes(RtnCode.TITLE_NOT_EXIST.getMessage());
		}
		String Questionnairetitle = questionnaire.get().getTitle();
		
		//尋找填寫該問題的人及資訊
		List<Users> users = usersDao.findByQuestionnaireTitle(Questionnairetitle);
		
		if(users == null) {
			return new AnsStatisticsRes(RtnCode.NO_STATISTICS_RESULT.getMessage());
		}
		
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

	//將資料庫中的答案轉成map方便統計
	private Map<String,Map<String,Integer>> statisticsFunction(List<String> usersAns) {

		Map<String,Map<String,Integer>> qaStatisticsMap = new HashMap<>();

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

	//將值存入map中
	private void mapPutStatistics(Map<String,Map<String,Integer>> qaStatisticsMap,
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

	}

	//顯示所有填寫人
	@Override
	public AnsStatisticsRes readAllUsers() {
		List<Users> resultList = usersDao.findAllByOrderByFinishTimeDesc();
		
		if(resultList == null) {
			return new AnsStatisticsRes(RtnCode.NO_USERS.getMessage());
		}
		
		AnsStatisticsRes res = new AnsStatisticsRes(RtnCode.SUCCESS.getMessage());
		res.setUsersList(resultList);
		return res;
	}

	//顯示單一填寫人填寫資料
	@Override
	public AnsStatisticsRes readUsersInfo(AnsStatisticsReq req) {
		Optional<Users> userOp = usersDao.findById(req.getUsersId());
		if(!userOp.isPresent()) {
			return new AnsStatisticsRes(RtnCode.NO_USERS.getMessage());
		}
		Users user = userOp.get();
		AnsStatisticsRes res = new AnsStatisticsRes(RtnCode.SUCCESS.getMessage());
		res.setUser(user);
		return res;
	}
	
}
