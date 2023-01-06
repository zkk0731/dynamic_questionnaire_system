package com.example.dynamic_questionnaire_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dynamic_questionnaire_system.constants.RtnCode;
import com.example.dynamic_questionnaire_system.service.ifs.AnsStatisticsService;
import com.example.dynamic_questionnaire_system.vo.AnsStatisticsReq;
import com.example.dynamic_questionnaire_system.vo.AnsStatisticsRes;

@CrossOrigin
@RestController
public class StatisticsController {
	
	@Autowired
	private AnsStatisticsService ansStatisticsService;
	
	//顯示統計資料
	//帶入問卷Id
	@PostMapping(value = "/ans_statistics")
	public AnsStatisticsRes AnsStatistics(@RequestBody AnsStatisticsReq req) {
		
		if(req.getQuestionnaireId() == 0) {
			return new AnsStatisticsRes(RtnCode.PARAMETER_REQUIRED.getMessage());
		}
		return ansStatisticsService.statistics(req);
		
	}
	
	@PostMapping(value = "/read_all_users")
	public AnsStatisticsRes readAllUsers(@RequestBody AnsStatisticsReq req) {
		if(!StringUtils.hasText(req.getQuestionnaireTitle())) {
			return new AnsStatisticsRes(RtnCode.PARAMETER_REQUIRED.getMessage());
		}
		
		return ansStatisticsService.readAllUsers(req);
	}
	
	@PostMapping(value = "/read_user_info")
	public AnsStatisticsRes readUserInfo(@RequestBody AnsStatisticsReq req) {
		
		if(req.getUsersId() == 0) {
			return new AnsStatisticsRes(RtnCode.PARAMETER_REQUIRED.getMessage());
		}
		return ansStatisticsService.readUsersInfo(req);
	}
}
