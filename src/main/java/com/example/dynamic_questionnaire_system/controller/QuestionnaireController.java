package com.example.dynamic_questionnaire_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dynamic_questionnaire_system.constants.RtnCode;
import com.example.dynamic_questionnaire_system.service.ifs.QuestionsService;
import com.example.dynamic_questionnaire_system.vo.QuestionsReq;
import com.example.dynamic_questionnaire_system.vo.QuestionsRes;

@CrossOrigin
@RestController
public class QuestionnaireController {

	@Autowired
	private QuestionsService questionsService;
	
	//創建問卷
	@PostMapping(value = "/create_questionnaire")
	public QuestionsRes createQuestionnaire(@RequestBody QuestionsReq req) {
		if(!StringUtils.hasText(req.getTitle()) ||
				!StringUtils.hasText(req.getDescription()) ||
				req.getStartTime() == null ||
				req.getEndTime() == null) {
			return new QuestionsRes(RtnCode.PARAMETER_REQUIRED.getMessage());
		}
		
		if(req.getStartTime().isAfter(req.getEndTime())) {
			return new QuestionsRes(RtnCode.PARAMETER_ERROR.getMessage());
		}
		
		return questionsService.createQuestionnaire(req);
	}
	
	//顯示所有問卷標題
	@PostMapping(value = "/read_all_questionnaire")
	public QuestionsRes readAllQuestionnaire() {
		return questionsService.readAllQuestionnaire();
	}
	
	//修改問卷
	//req 需有QuestionnaireId 
	@PostMapping(value = "/update_questionnaire")
	public QuestionsRes updateQuestionnaire(@RequestBody QuestionsReq req) {
		if(req.getQuestionnaireId() == 0 ||
				!StringUtils.hasText(req.getTitle()) ||
				!StringUtils.hasText(req.getDescription()) ||
				req.getStartTime() == null ||
				req.getEndTime() == null) {
			return new QuestionsRes(RtnCode.PARAMETER_REQUIRED.getMessage());
		}
		
		if(req.getStartTime().isAfter(req.getEndTime())) {
			return new QuestionsRes(RtnCode.PARAMETER_ERROR.getMessage());
		}
		
		return questionsService.updateQuestionnaire(req);
	}
	
	//刪除問卷
	//req 需有QuestionnaireIdList
	@PostMapping(value = "/delete_questionnaire")
	public QuestionsRes deleteeQuestionnaire(@RequestBody QuestionsReq req) {
		if(req.getQuestionnaireIdList() == null) {
			return new QuestionsRes(RtnCode.NO_DELETED_QUESTIONNAIRE.getMessage());
		}
		return questionsService.deleteQuestionnaire(req);
	}
	
	//顯示指定問卷的問答內容
	//req 需有QuestionnaireId
	@PostMapping(value = "/read_Qa_by_title")
	public QuestionsRes readQaByQuestionnaireTitle(@RequestBody QuestionsReq req) {
		if(req.getQuestionnaireId() == 0) {
			return new QuestionsRes(RtnCode.NO_QUESTIONNAIRE.getMessage());
		}
		
		return questionsService.readQaByQuestionnaireTitle(req);
	}
	
	//刪除題目
	@PostMapping(value = "/delete_Qa")
	public QuestionsRes deleteQa(@RequestBody QuestionsReq req) {
		if(req.getQaIdList() == null) {
			return new QuestionsRes(RtnCode.NO_DELETED_QA.getMessage());
		}
		
		return questionsService.deleteQAndA(req);
	}
}
