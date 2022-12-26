package com.example.dynamic_questionnaire_system;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
		req.setQuestionnaireTitle("test3");
		AnsStatisticsRes res = ansStatisticsService.statistics(req);
		System.out.println(res.getMessage());
		Map<String, Map<String, Integer>> result = res.getQaStatisticsMap();
		System.out.println(result.toString());
		
	}
}
