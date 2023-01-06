package com.example.dynamic_questionnaire_system;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.dynamic_questionnaire_system.entity.Users;
import com.example.dynamic_questionnaire_system.service.ifs.AnsStatisticsService;
import com.example.dynamic_questionnaire_system.vo.AnsStatisticsReq;
import com.example.dynamic_questionnaire_system.vo.AnsStatisticsRes;
import com.example.dynamic_questionnaire_system.vo.QuestionsReq;

@SpringBootTest
public class StatisticsTest {

	@Autowired
	private AnsStatisticsService ansStatisticsService;
	
	@Test
	public void statisticsAns() {
		AnsStatisticsReq req = new AnsStatisticsReq();
		req.setQuestionnaireId(15);
		AnsStatisticsRes res = ansStatisticsService.statistics(req);
		System.out.println(res.getMessage());
		Map<String, Map<String, Integer>> result = res.getQaStatisticsMap();
		System.out.println(result.toString());
		
	}
	
	@Test
	public void readAllUsersTest() {
		AnsStatisticsReq req = new AnsStatisticsReq();
		req.setQuestionnaireTitle("test3");
		
		AnsStatisticsRes res = ansStatisticsService.readAllUsers(req);
		
		System.out.println(res.getMessage());
		
		for(Users item : res.getUsersList()) {
			System.out.println(item.getName()+ " " + item.getFinishTime());
		}
		
	}
	
	@Test
	public void readUserTest() {
		AnsStatisticsReq req = new AnsStatisticsReq();
		req.setUsersId(1);
		AnsStatisticsRes res = ansStatisticsService.readUsersInfo(req);
		System.out.println(res.getMessage());
		Users user = res.getUser();
		
		System.out.println(user.getName());
		System.out.println(user.getUserAns());
	}
}
