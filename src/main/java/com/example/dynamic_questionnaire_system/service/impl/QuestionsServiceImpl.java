package com.example.dynamic_questionnaire_system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.dynamic_questionnaire_system.constants.RtnCode;
import com.example.dynamic_questionnaire_system.entity.Questionnaire;
import com.example.dynamic_questionnaire_system.entity.QuestionsAndAns;
import com.example.dynamic_questionnaire_system.repository.QuestionnaireDao;
import com.example.dynamic_questionnaire_system.repository.QuestionsAndAnsDao;
import com.example.dynamic_questionnaire_system.service.ifs.QuestionsService;
import com.example.dynamic_questionnaire_system.vo.QuestionsReq;
import com.example.dynamic_questionnaire_system.vo.QuestionsRes;

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
		
		
		if(req.getQaList() != null) {
			//設定及檢查問答格式
			res = setQaTitleAndSave(req.getQaList(), req.getTitle());
			
			if(res != null) {
				return res;
			}
		}

		questionnaireDao.save(questionnaireList);
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
	public QuestionsRes updateQuestionnaire(QuestionsReq req) {
		Optional<Questionnaire> questionnaireOp = questionnaireDao.findById(req.getQuestionnaireId());
		
		//判斷問卷是否存在
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
		
		//更新問卷
		updateQaList = updateQa(oldQa,updateQaList, req.getTitle());
		
		//新增重複問題擋掉
		if(updateQaList == null) {
			return new QuestionsRes(RtnCode.QUESTION_EXIST.getMessage());
		}
		
		saveDb(updateQaList,questionnaire);
//		questionsAndAnsDao.saveAll(updateQaList);
//		questionnaireDao.save(questionnaire);
		return new QuestionsRes(RtnCode.SUCCESS.getMessage());
	}

	@Transactional
	public void saveDb(List<QuestionsAndAns> updateQaList, Questionnaire questionnaire) {
		questionsAndAnsDao.saveAll(updateQaList);
		questionnaireDao.save(questionnaire);
	}
	
	//更新QA
	private List<QuestionsAndAns> updateQa(List<QuestionsAndAns> oldQa, List<QuestionsAndAns> updateQaList, String newTitle) {
		List<QuestionsAndAns> addNewQuestion = new ArrayList<>();
		Map<String,QuestionsAndAns> addNewQuestionMap = new HashMap<>();
		
		for(QuestionsAndAns item : oldQa) {
			item.setQuestionnaireTitle(newTitle);
			addNewQuestionMap = new HashMap<>();
			if(updateQaList != null) {
				for(QuestionsAndAns item2 : updateQaList) {
					
					if(item.getId() == item2.getId()){
					
						item.setQuestions(item2.getQuestions());
						item.setAns(item2.getAns());
						item.setOneOrMany(item2.isOneOrMany());
						item.setNecessary(item2.isNecessary());
						addNewQuestionMap.remove(item2.getQuestions());
						break;
					}else if(addNewQuestionMap.containsKey(item2.getQuestions()) ||
							addNewQuestionMap.containsKey(item.getQuestions())
							){
						return null;
					}else {
						item2.setQuestionnaireTitle(newTitle);
						addNewQuestionMap.put(item2.getQuestions(), item2);
					}
				}
			}
		}
		
		for(Entry<String, QuestionsAndAns> item3:addNewQuestionMap.entrySet()) {
			
			oldQa.add(item3.getValue());
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

	//搜尋
	@Override
	public QuestionsRes search(QuestionsReq req) {
		QuestionsRes res = new QuestionsRes();
		List<Questionnaire> result = new ArrayList<>();
		int caseInt = searchParamCheck(req);
		switch (caseInt) {
			case 0:
				result = questionnaireDao.findAll();
				break;
			
			case 1:
				result = questionnaireDao.findByTitleContaining(req.getTitle());
				break;
			
			case 2:
				List<Questionnaire> questionnaireList1 = questionnaireDao.findByTitleContaining(req.getTitle());
				for(Questionnaire item : questionnaireList1) {
					if(item.getStartTime().isAfter(req.getStartTime()) ||
							item.getStartTime().equals(req.getStartTime())) {
						result.add(item);
					}
				}
				break;
			
			case 3:
				List<Questionnaire> questionnaireList2 = questionnaireDao.findByTitleContaining(req.getTitle());
				for(Questionnaire item : questionnaireList2) {
					if(item.getEndTime().isBefore(req.getEndTime()) ||
							item.getEndTime().equals(req.getEndTime())) {
						result.add(item);
					}
				}
				break;
				
			case 4:
				List<Questionnaire> questionnaireList3 = questionnaireDao.findByTitleContaining(req.getTitle());
				for(Questionnaire item : questionnaireList3) {
					if(!item.getEndTime().isAfter(req.getEndTime()) &&
							!item.getStartTime().isBefore(req.getStartTime())
							) {
						result.add(item);
					}
				}
				break;
				
			case 5:
				List<Questionnaire> questionnaireList4 = questionnaireDao.findAll();
				for(Questionnaire item : questionnaireList4) {
					if(item.getStartTime().isAfter(req.getStartTime()) ||
							item.getStartTime().equals(req.getStartTime())) {
						result.add(item);
					}
				}
				break;
				
			case 6:
				List<Questionnaire> questionnaireList5 = questionnaireDao.findAll();
				for(Questionnaire item : questionnaireList5) {
					if(item.getEndTime().isBefore(req.getEndTime()) ||
							item.getEndTime().equals(req.getEndTime())) {
						result.add(item);
					}
				}
				break;
				
			case 7:
				List<Questionnaire> questionnaireList6 = questionnaireDao.findAll();
				for(Questionnaire item : questionnaireList6) {
					if(!item.getEndTime().isAfter(req.getEndTime()) &&
							!item.getStartTime().isBefore(req.getStartTime())) {
						result.add(item);
					}
				}
				break;
		}
		
		if(CollectionUtils.isEmpty(result)) {
			return new QuestionsRes(RtnCode.NO_QUESTIONNAIRE.getMessage());
		}
		
		res.setQuestionnaireList(result);
		res.setMessage(RtnCode.SUCCESS.getMessage());
		return res;
	}
	
	private int searchParamCheck(QuestionsReq req) {
		//指輸入文字
		if(StringUtils.hasText(req.getTitle()) 
				&& req.getStartTime() == null 
				&& req.getEndTime() == null) {
			return 1;
		}//輸入文字跟開始時間
		else if(StringUtils.hasText(req.getTitle()) 
				&& req.getStartTime() != null 
				&& req.getEndTime() == null) {
			return 2;
		}//輸入文字跟結束時間
		else if(StringUtils.hasText(req.getTitle()) 
				&& req.getStartTime() == null 
				&& req.getEndTime() != null) {
			return 3;
		}//輸入文字跟開始結束時間
		else if(StringUtils.hasText(req.getTitle()) 
				&& req.getStartTime() != null 
				&& req.getEndTime() != null) {
			return 4;
		}//只輸入開始時間
		else if(!StringUtils.hasText(req.getTitle()) 
				&& req.getStartTime() != null 
				&& req.getEndTime() == null) {
			return 5;
		}//只輸入結束時間
		else if(!StringUtils.hasText(req.getTitle()) 
				&& req.getStartTime() == null 
				&& req.getEndTime() != null) {
			return 6;
		}//輸入開始結束時間
		else if(!StringUtils.hasText(req.getTitle()) 
				&& req.getStartTime() != null 
				&& req.getEndTime() != null) {
			return 7;
		}
		return 0;
	}

	//確認標題是否重複
	@Override
	public QuestionsRes checkTitleDuplicate(QuestionsReq req) {
		Questionnaire questionnaireList = questionnaireDao.findByTitle(req.getTitle());
		
		//判斷問卷名稱是否存在
		if(questionnaireList != null) {
			return new QuestionsRes(RtnCode.TITLE_ALREADY_EXIST.getMessage());
		}
		return new QuestionsRes(RtnCode.SUCCESS.getMessage());
	}

}
