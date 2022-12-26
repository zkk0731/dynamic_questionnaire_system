package com.example.dynamic_questionnaire_system.service.ifs;

import com.example.dynamic_questionnaire_system.vo.QuestionsReq;
import com.example.dynamic_questionnaire_system.vo.QuestionsRes;

public interface QuestionsService {
	
	
	//創建問卷
	public QuestionsRes createQuestionnaire(QuestionsReq req);
	
	//顯示所有問卷
	public QuestionsRes readAllQuestionnaire();
	
	//更新問卷
	public QuestionsRes updateQuestionnaire(QuestionsReq req);
	
	//刪除問卷
	public QuestionsRes deleteQuestionnaire(QuestionsReq req);
	
	//顯示指定問卷的問答內容
	public QuestionsRes readQaByQuestionnaireTitle(QuestionsReq req);
	
	//刪除題目
	public QuestionsRes deleteQAndA(QuestionsReq req);
	
	//搜尋
	public QuestionsRes search(QuestionsReq req);
}
