package com.example.dynamic_questionnaire_system.service.impl;

import java.time.LocalDate;
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
import com.example.dynamic_questionnaire_system.entity.Users;
import com.example.dynamic_questionnaire_system.repository.QuestionnaireDao;
import com.example.dynamic_questionnaire_system.repository.QuestionsAndAnsDao;
import com.example.dynamic_questionnaire_system.repository.UsersDao;
import com.example.dynamic_questionnaire_system.service.ifs.QuestionsService;
import com.example.dynamic_questionnaire_system.vo.QuestionsReq;
import com.example.dynamic_questionnaire_system.vo.QuestionsRes;
import com.example.dynamic_questionnaire_system.vo.WriteQuestionnaireReq;

@Service
public class QuestionsServiceImpl implements QuestionsService{

	
	@Autowired
	private QuestionnaireDao questionnaireDao;
	
	@Autowired
	private QuestionsAndAnsDao questionsAndAnsDao;
	
	@Autowired
	private UsersDao usersDao;
	
	@Override
	@Transactional
	public QuestionsRes createQuestionnaire(QuestionsReq req) {
		QuestionsRes res = new QuestionsRes();
//		Questionnaire questionnaireList = questionnaireDao.findByTitle(req.getTitle());
		
		//判斷問卷名稱是否存在
		if(questionnaireDao.existsByTitle(req.getTitle())) {
			return new QuestionsRes(RtnCode.TITLE_ALREADY_EXIST.getMessage());
		}
		
		Questionnaire questionnaireList = new Questionnaire(
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
		List<Questionnaire> result = questionnaireDao.findAllByOrderByIdDesc();
		
		//判斷DB裡是否有問卷
		if(CollectionUtils.isEmpty(result)) {
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
		
		//將問卷資料更新
		questionnaire.setTitle(req.getTitle());
		questionnaire.setDescription(req.getDescription());
		questionnaire.setStartTime(req.getStartTime());
		questionnaire.setEndTime(req.getEndTime());
		
		List<QuestionsAndAns> updateQaList = req.getQaList();
		List<QuestionsAndAns> oldQa = questionsAndAnsDao.findByQuestionnaireTitle(oldTitle);
		
		//更新問卷 舊的值改成新的值 問題重複會回傳null
		updateQaList = updateQa(oldQa,updateQaList, req.getTitle());
		
		//新增重複問題擋掉
		if(updateQaList == null) {
			return new QuestionsRes(RtnCode.QUESTION_EXIST.getMessage());
		}
		
		saveDb(updateQaList,questionnaire);
		
		return new QuestionsRes(RtnCode.SUCCESS.getMessage());
	}

	//存進資料庫
	@Transactional
	public void saveDb(List<QuestionsAndAns> updateQaList, Questionnaire questionnaire) {
		questionsAndAnsDao.saveAll(updateQaList);
		questionnaireDao.save(questionnaire);
	}
	
	//更新QA
	private List<QuestionsAndAns> updateQa(List<QuestionsAndAns> oldQa, List<QuestionsAndAns> updateQaList, String newTitle) {
		Map<String,QuestionsAndAns> addNewQuestionMap = new HashMap<>();
		
		for(QuestionsAndAns item : oldQa) {
			
			//所有題目title改成新的
			item.setQuestionnaireTitle(newTitle);
			addNewQuestionMap = new HashMap<>();
			
			if(!CollectionUtils.isEmpty(updateQaList)) {
				for(QuestionsAndAns item2 : updateQaList) {
					
					//判斷問題是否重複
					if(addNewQuestionMap.containsKey(item2.getQuestions()) ||
							addNewQuestionMap.containsKey(item.getQuestions())){
						return null;
						
					}
					
					//找到對應ID題目進行更新
					else if(item.getId() == item2.getId()){
		
						item.setQuestions(item2.getQuestions());
						item.setAns(item2.getAns());
						item.setOneOrMany(item2.isOneOrMany());
						item.setNecessary(item2.isNecessary());
						
						//因為是原有的問題更新 將他從map拿掉
						addNewQuestionMap.remove(item2.getQuestions());
						
					}else {
						
						//問題放入map
						item2.setQuestionnaireTitle(newTitle);
						addNewQuestionMap.put(item2.getQuestions(), item2);
					}
				}
			}
		}
		
		//map裡有新增的題目 加進原有的問題list
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
		
		//刪除此title的問卷 包含題目
		questionsAndAnsDao.deleteByQuestionnaireTitleIn(req.getTitleList());
		questionnaireDao.deleteByTitleIn(req.getTitleList());
		
		return new QuestionsRes(RtnCode.SUCCESS.getMessage());
	}

	//顯示指定問卷的問答內容
	@Override
	public QuestionsRes readQaByQuestionnaireTitle(QuestionsReq req) {
		Optional<Questionnaire> resultOp = questionnaireDao.findById(req.getQuestionnaireId());
		
		//判斷問卷是否存在
		if(!resultOp.isPresent()) {
			return new QuestionsRes(RtnCode.TITLE_NOT_EXIST.getMessage());
		}

		Questionnaire questionnaire = resultOp.get();
		//從DB取出該問卷所有題目
		List<QuestionsAndAns> qaList = questionsAndAnsDao.findByQuestionnaireTitle(resultOp.get().getTitle());
		
		//沒題目回傳訊息
		if(CollectionUtils.isEmpty(qaList)) {
			return new QuestionsRes(RtnCode.QA_NOT_EXIST.getMessage());
		}
		
		//回傳取得的題目
		QuestionsRes res = new QuestionsRes();
		res.setStartTime(questionnaire.getStartTime());
		res.setEndTime(questionnaire.getEndTime());
		res.setTitle(questionnaire.getTitle());
		res.setDescription(questionnaire.getDescription());
		res.setQaList(qaList);
		return res;
	}

	//刪除題目
	@Override
	@Transactional
	public QuestionsRes deleteQAndA(QuestionsReq req) {
		
		//由題目ID來刪除
//		questionsAndAnsDao.de
		questionsAndAnsDao.deleteByIdIn(req.getQaIdList());
		return new QuestionsRes(RtnCode.SUCCESS.getMessage());
	}

	//搜尋
	@Override
	public QuestionsRes search(QuestionsReq req) {
		String searchText = StringUtils.hasText(req.getTitle()) ? req.getTitle() : "";
		LocalDate startDate = req.getStartTime() == null ? LocalDate.of(1970, 1, 1) : req.getStartTime();
		LocalDate endDate = req.getEndTime() == null ? LocalDate.of(2999, 1, 1) : req.getEndTime();
		
		QuestionsRes res = new QuestionsRes();
		List<Questionnaire> result = new ArrayList<>();
		result = questionnaireDao.findByTitleContainingAndStartTimeGreaterThanEqualAndEndTimeLessThanEqualOrderByIdDesc(searchText, startDate, endDate);
		
		
		//篩選後沒得到結果回傳訊息
		if(CollectionUtils.isEmpty(result)) {
			return new QuestionsRes(RtnCode.NO_QUESTIONNAIRE.getMessage());
		}
		
		res.setQuestionnaireList(result);
		res.setMessage(RtnCode.SUCCESS.getMessage());
		return res;
	}
	

	//確認標題是否重複
	@Override
	public QuestionsRes checkTitleDuplicate(QuestionsReq req) {
		
		//判斷問卷名稱是否存在
		if(questionnaireDao.existsByTitle(req.getTitle())) {
			return new QuestionsRes(RtnCode.TITLE_ALREADY_EXIST.getMessage());
		}
		
		return new QuestionsRes(RtnCode.SUCCESS.getMessage());
	}

	//填寫問卷
	@Override
	public QuestionsRes writeQuestionnaire(WriteQuestionnaireReq req) {
		
		//判斷使用者填寫格式是否正確
		QuestionsRes check = checkWriteQuestionnaireParam(req);
		if(check != null) {
			return check;
		}
		
		//將使用者填寫的問卷map轉乘String存入DB
		Map<String, String> ansMap = req.getUserAns();
		String ansString = ansMap.toString().substring(1, ansMap.toString().length() - 1);
		
		Users users = new Users(req.getName(), req.getPhone(), req.getEmail(), req.getAge(),
				ansString, req.getFinishTime(), req.getQuestionnaireTitle(), req.getGender());
		
		//所有資料存入DB
		usersDao.save(users);
		return new QuestionsRes(RtnCode.SUCCESS.getMessage());
	}

	//判斷電話 信箱 年齡格式
	private QuestionsRes checkWriteQuestionnaireParam(WriteQuestionnaireReq req) {

		String phonePattern = "09\\d{8}";
		String emailPattern = "[A-za-z0-9]+@[A-za-z0-9]+\\.com";
		
		if(!req.getEmail().matches(emailPattern) ||
				!req.getPhone().matches(phonePattern) ||
				req.getAge() <= 0) {
			return new QuestionsRes(RtnCode.PARAMETER_ERROR.getMessage());
		}
		
		return null;
	}
	
}
