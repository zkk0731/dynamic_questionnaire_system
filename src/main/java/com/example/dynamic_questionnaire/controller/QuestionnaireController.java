package com.example.dynamic_questionnaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dynamic_questionnaire.constants.RtnCode;
import com.example.dynamic_questionnaire.service.ifs.QuestionsService;
import com.example.dynamic_questionnaire.vo.QuestionsReq;
import com.example.dynamic_questionnaire.vo.QuestionsRes;

@RestController
public class QuestionnaireController {

	@Autowired
	private QuestionsService questionsService;
	
	//創建問卷
	@PostMapping(value = "/create_questionnaire")
	public QuestionsRes createQuestionnaire(@RequestBody QuestionsReq req) {
		if(StringUtils.hasText(req.getTitle()) ||
				StringUtils.hasText(req.getDescription()) ||
				req.getStartTime() == null ||
				req.getEndTime() == null) {
			return new QuestionsRes(RtnCode.PARAMETER_REQUIRED.getMessage());
		}
		
		if(req.getStartTime().isAfter(req.getEndTime())) {
			return new QuestionsRes(RtnCode.PARAMETER_ERROR.getMessage());
		}
		
		return questionsService.createQuestionnaire(req);
	}
	
	//顯示所有問卷
	@PostMapping(value = "/show_all_questionnaire")
	public QuestionsRes showAllQuestionnaire() {
		return questionsService.showAllQuestionnaire();
	}
	
	@PostMapping(value = "/update_questionnaire")
	public QuestionsRes updateQuestionnaire(@RequestBody QuestionsReq req) {
		if(StringUtils.hasText(req.getTitle()) ||
				StringUtils.hasText(req.getDescription()) ||
				req.getStartTime() == null ||
				req.getEndTime() == null) {
			return new QuestionsRes(RtnCode.PARAMETER_REQUIRED.getMessage());
		}
		
		if(req.getStartTime().isAfter(req.getEndTime())) {
			return new QuestionsRes(RtnCode.PARAMETER_ERROR.getMessage());
		}
		
		return questionsService.updateQuestionnaire(req);
	}
	
}
