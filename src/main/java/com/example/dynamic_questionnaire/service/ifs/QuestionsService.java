package com.example.dynamic_questionnaire.service.ifs;

import com.example.dynamic_questionnaire.vo.QuestionsReq;
import com.example.dynamic_questionnaire.vo.QuestionsRes;

public interface QuestionsService {
	
	
	//創建問卷
	public QuestionsRes createQuestionnaire(QuestionsReq req);
	
	//顯示所有問卷
	public QuestionsRes showAllQuestionnaire();
	
	//更新問卷
	public QuestionsRes updateQuestionnaire(QuestionsReq req);
}
