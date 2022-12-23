package com.example.dynamic_questionnaire;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.dynamic_questionnaire.entity.Questionnaire;
import com.example.dynamic_questionnaire.entity.QuestionsAndAns;
import com.example.dynamic_questionnaire.service.ifs.QuestionsService;
import com.example.dynamic_questionnaire.vo.QuestionsReq;
import com.example.dynamic_questionnaire.vo.QuestionsRes;

@SpringBootTest
public class CreateQaTest {

	@Autowired
	private QuestionsService questionsService;
	
	@Test
	public void createTest1() {
		QuestionsReq req = new QuestionsReq();
		req.setTitle("Test4");
		req.setDescription("For test4");
		QuestionsAndAns qa1 = new QuestionsAndAns("q1","a1;a2;a3",false,false);
		QuestionsAndAns qa2 = new QuestionsAndAns("q2","a1;a2;a3",false,false);
		QuestionsAndAns qa3 = new QuestionsAndAns("q3","a1;a2;a3",false,false);
		List<QuestionsAndAns> qaList = Arrays.asList(qa1,qa2,qa3);
		
		req.setQaList(qaList);
		String dateStr1 = "2022-10-01";
		String dateStr2 = "2022-11-30";
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate date1 = LocalDate.parse(dateStr1,format);
		LocalDate date2 = LocalDate.parse(dateStr2,format);
		
		req.setStartTime(date1);
		req.setEndTime(date2);
		
		QuestionsRes res = questionsService.createQuestionnaire(req);
		System.out.println(res.getMessage());
	}
	
	@Test
	public void showAllQuestionnaire() {
		QuestionsRes result = questionsService.readAllQuestionnaire();
		List<Questionnaire> list = result.getQuestionnaireList();
		for(Questionnaire item : list) {
			System.out.println(item.getTitle());
		}
	}
	
	@Test
	public void updateQuestionnaire() {
		QuestionsReq req = new QuestionsReq();
		req.setQuestionnaireId(4);
		req.setTitle("new Test424");
		req.setDescription("For new test424");
		
//		QuestionsAndAns qa1 = new QuestionsAndAns("Test4","q4","b;b",true,false);
//		qa1.setId(5);
//		QuestionsAndAns qa2 = new QuestionsAndAns("Test4","q5","b;b",false,false);
//		qa2.setId(6);
//		QuestionsAndAns qa3 = new QuestionsAndAns("Test4","q6","b;b;b",false,false);
//		qa3.setId(7);
//		List<QuestionsAndAns> qaList = Arrays.asList(qa1,qa2,qa3);
//		req.setQaList(qaList);
		
		String dateStr1 = "2022-11-01";
		String dateStr2 = "2022-11-20";
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		LocalDate date1 = LocalDate.parse(dateStr1,format);
		LocalDate date2 = LocalDate.parse(dateStr2,format);
		
		req.setStartTime(date1);
		req.setEndTime(date2);
		
		QuestionsRes res = questionsService.updateQuestionnaire(req);
		System.out.println(res.getMessage());
	}
	
	@Test
	public void deleteQuestionnaire() {
		List<Integer> idList = Arrays.asList(13,14);
		QuestionsReq req = new QuestionsReq();
		req.setQuestionnaireIdList(idList);
		QuestionsRes res = questionsService.deleteQuestionnaire(req);
		System.out.println(res.getMessage());
		
	}
	
	@Test
	public void showQaByTitle() {
		QuestionsReq req = new QuestionsReq();
		req.setQuestionnaireId(15);
		QuestionsRes res = questionsService.readQaByQuestionnaireTitle(req);
		
		List<QuestionsAndAns> result = res.getQaList();
		for(QuestionsAndAns item : result) {
			System.out.println(item.getQuestions()+" "+item.getAns());
		}
	}
	
	@Test
	public void deleteQa() {
		QuestionsReq req = new QuestionsReq();
		List<Integer> idList = Arrays.asList(42,43);
		req.setQaIdList(idList);
		QuestionsRes res = questionsService.deleteQAndA(req);
		System.out.println(res.getMessage());
	}
	
}
