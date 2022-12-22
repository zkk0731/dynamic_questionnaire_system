package com.example.dynamic_questionnaire.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
			questionsAndAnsDao.saveAll(req.getQaList());
		}
		res.setMessage(RtnCode.SUCCESS.getMessage());
		return res;
	}

	//顯示所有問卷
	@Override
	public QuestionsRes showAllQuestionnaire() {
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
	

}
