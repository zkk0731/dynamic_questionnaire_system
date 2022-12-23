package com.example.dynamic_questionnaire.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.dynamic_questionnaire.constants.RtnCode;
import com.example.dynamic_questionnaire.entity.Questionnaire;
import com.example.dynamic_questionnaire.entity.QuestionsAndAns;
import com.example.dynamic_questionnaire.repository.QuestionnaireDao;
import com.example.dynamic_questionnaire.repository.QuestionsAndAnsDao;
import com.example.dynamic_questionnaire.service.ifs.QuestionsService;
import com.example.dynamic_questionnaire.vo.QuestionsReq;
import com.example.dynamic_questionnaire.vo.QuestionsRes;

@Service
public class QuestionsServiceImpl implements QuestionsService{

	
	@Autowired
	private QuestionnaireDao questionnaireDao;
	
	@Autowired
	private QuestionsAndAnsDao questionsAndAnsDao;
	
	@Override
	@Transactional
	public QuestionsRes createQuestionnaire(QuestionsReq req) {
		QuestionsRes res = new QuestionsRes();
		Questionnaire questionnaireList = questionnaireDao.findByTitle(req.getTitle());
		
		//判斷問卷名稱是否存在
		if(questionnaireList != null) {
			return new QuestionsRes(RtnCode.TITLE_ALREADY_EXIST.getMessage());
		}
		
		questionnaireList = new Questionnaire(
				req.getTitle(),
				req.getDescription(),
				req.getStartTime(),
				req.getEndTime());
		
		
		questionnaireDao.save(questionnaireList);
		
		if(req.getQaList() != null) {
			//設定及檢查問答格式
			res = setQaTitleAndSave(req.getQaList(), req.getTitle());
		}
		
		if(res != null) {
			return res;
		}
		
		return new QuestionsRes(RtnCode.SUCCESS.getMessage());
	}
	
	//設定及檢查問答
	private QuestionsRes setQaTitleAndSave(List<QuestionsAndAns> qaList, String questionnaireTitle) {
		for(QuestionsAndAns item : qaList) {
			//問題與答案必須都填
			if(!StringUtils.hasText(item.getQuestions()) || !StringUtils.hasText(item.getAns())) {
				return new QuestionsRes(RtnCode.QA_REQUIRED.getMessage());
			}
			
			//答案需有;分隔
			if(!item.getAns().contains(";")) {
				return new QuestionsRes(RtnCode.ANS_PARAMETER_ERROR.getMessage());
			}
			
			item.setQuestionnaireTitle(questionnaireTitle);
		}
		questionsAndAnsDao.saveAll(qaList);
		return null;
	}
	
	//顯示所有問卷
	@Override
	public QuestionsRes readAllQuestionnaire() {
		List<Questionnaire> result = questionnaireDao.findAll();
		if(result == null) {
			return new QuestionsRes(RtnCode.NO_QUESTIONNAIRE.getMessage());
		}
		
		QuestionsRes res = new QuestionsRes(RtnCode.SUCCESS.getMessage());
		res.setQuestionnaireList(result);
		return res;
	}

	//更新問卷
	@Override
	@Transactional
	public QuestionsRes updateQuestionnaire(QuestionsReq req) {
		Optional<Questionnaire> questionnaireOp = questionnaireDao.findById(req.getQuestionnaireId());
		
		if(!questionnaireOp.isPresent()) {
			return new QuestionsRes(RtnCode.NO_QUESTIONNAIRE.getMessage());
		}
		Questionnaire questionnaire = questionnaireOp.get();
		String oldTitle = questionnaire.getTitle();
		
		questionnaire.setTitle(req.getTitle());
		questionnaire.setDescription(req.getDescription());
		questionnaire.setStartTime(req.getStartTime());
		questionnaire.setEndTime(req.getEndTime());
		
		List<QuestionsAndAns> updateQaList = req.getQaList();
		
		List<QuestionsAndAns> oldQa = questionsAndAnsDao.findByQuestionnaireTitle(oldTitle);
		
		updateQaList = updateQa(oldQa,updateQaList, req.getTitle());
		
		questionnaireDao.save(questionnaire);
		questionsAndAnsDao.saveAll(updateQaList);
		return new QuestionsRes(RtnCode.SUCCESS.getMessage());
	}

	//更新QA
	private List<QuestionsAndAns> updateQa(List<QuestionsAndAns> oldQa, List<QuestionsAndAns> updateQaList, String newTitle) {
		for(QuestionsAndAns item : oldQa) {
			item.setQuestionnaireTitle(newTitle);
			if(updateQaList != null) {
				for(QuestionsAndAns item2 : updateQaList) {
					if(item.getId() == item2.getId()){
					
						item.setQuestions(item2.getQuestions());
						item.setAns(item2.getAns());
						item.setOneOrMany(item2.isOneOrMany());
						item.setNecessary(item2.isNecessary());
						break;
					}
				}
			}
		}
		return oldQa;
	}

	//刪除問卷包含問答
	//一併刪除兩張表
	@Transactional
	@Override
	public QuestionsRes deleteQuestionnaire(QuestionsReq req) {
		List<Questionnaire> resultList = questionnaireDao.findAllById(req.getQuestionnaireIdList());
		
		if(CollectionUtils.isEmpty(resultList)) {
			return new QuestionsRes(RtnCode.TITLE_NOT_EXIST.getMessage());
		}
		
		List<String> titleList = new ArrayList<>();
		
		for(Questionnaire item : resultList) {
			titleList.add(item.getTitle());
		}

		questionsAndAnsDao.deleteByQuestionnaireTitleIn(titleList);
		questionnaireDao.deleteAll(resultList);
		return new QuestionsRes(RtnCode.SUCCESS.getMessage());
	}

	//顯示指定問卷的問答內容
	@Override
	public QuestionsRes readQaByQuestionnaireTitle(QuestionsReq req) {
		Optional<Questionnaire> resultOp = questionnaireDao.findById(req.getQuestionnaireId());
		if(!resultOp.isPresent()) {
			return new QuestionsRes(RtnCode.TITLE_NOT_EXIST.getMessage());
		}

		List<QuestionsAndAns> qaList = questionsAndAnsDao.findByQuestionnaireTitle(resultOp.get().getTitle());
		if(CollectionUtils.isEmpty(qaList)) {
			return new QuestionsRes(RtnCode.QA_NOT_EXIST.getMessage());
		}
		
		QuestionsRes res = new QuestionsRes();
		res.setQaList(qaList);
		return res;
	}

	//刪除題目
	@Override
	@Transactional
	public QuestionsRes deleteQAndA(QuestionsReq req) {
		questionsAndAnsDao.deleteAllById(req.getQaIdList());
		return new QuestionsRes(RtnCode.SUCCESS.getMessage());
	}
	

}
