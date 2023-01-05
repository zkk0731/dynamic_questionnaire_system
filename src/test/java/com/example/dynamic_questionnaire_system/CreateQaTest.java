package com.example.dynamic_questionnaire_system;

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

import com.example.dynamic_questionnaire_system.entity.Questionnaire;
import com.example.dynamic_questionnaire_system.entity.QuestionsAndAns;
import com.example.dynamic_questionnaire_system.service.ifs.QuestionsService;
import com.example.dynamic_questionnaire_system.vo.QuestionsReq;
import com.example.dynamic_questionnaire_system.vo.QuestionsRes;
import com.example.dynamic_questionnaire_system.vo.WriteQuestionnaireReq;

@SpringBootTest
public class CreateQaTest {

	@Autowired
	private QuestionsService questionsService;
	
	@Test
	public void createTest1() {
		QuestionsReq req = new QuestionsReq();
		req.setTitle("Test6");
		req.setDescription("For test6");
//		QuestionsAndAns qa1 = new QuestionsAndAns("q1","a1;a2;a3",false,false);
//		QuestionsAndAns qa2 = new QuestionsAndAns("q2","a1;a2;a3",false,false);
//		QuestionsAndAns qa3 = new QuestionsAndAns("q3","a1;a2;a3",false,false);
//		List<QuestionsAndAns> qaList = Arrays.asList(qa1,qa2,qa3);
		
//		req.setQaList(qaList);
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
		
		QuestionsAndAns qa1 = new QuestionsAndAns("q4","===;b11",true,false);
		qa1.setId(5);
		QuestionsAndAns qa2 = new QuestionsAndAns("q15","b456;b",false,false);
		qa2.setId(6);
		QuestionsAndAns qa3 = new QuestionsAndAns("q16","b;b;b",false,false);
		qa3.setId(7);
		QuestionsAndAns qa4 = new QuestionsAndAns("q17","b;b;b",false,false);
		QuestionsAndAns qa5 = new QuestionsAndAns("q18","a;c;d",false,false);
		List<QuestionsAndAns> qaList = Arrays.asList(qa1,qa2,qa3,qa4,qa5);
		req.setQaList(qaList);
		
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
		List<String> idList = Arrays.asList("Test5","Test6");
		QuestionsReq req = new QuestionsReq();
		req.setTitleList(idList);
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
		List<Integer> idList = Arrays.asList(52,53);
		req.setQaIdList(idList);
		QuestionsRes res = questionsService.deleteQAndA(req);
		System.out.println(res.getMessage());
	}
	
	@Test
	public void splitTest() {
		String a = "aa=";
		String[] b = a.split("=");
		for(String item : b) {
			System.out.println(item);
		}
		System.out.println(b.length);
	}
	
	@Test
	public void searchTest() {
		QuestionsReq req = new QuestionsReq();
//		req.setTitle("t");
		
		String dateStr1 = "2022-11-01";
		String dateStr2 = "2022-12-01";
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate date1 = LocalDate.parse(dateStr1,format);
		LocalDate date2 = LocalDate.parse(dateStr2,format);
		
//		req.setStartTime(date1);
//		req.setEndTime(date2);
		
		QuestionsRes res = questionsService.search(req);
		System.out.println(res.getMessage());
		
		List<Questionnaire> result = res.getQuestionnaireList();
		if(result != null) {
			for(Questionnaire item : result) {
				System.out.println(item.getTitle() + " " + item.getStartTime() +" "+item.getEndTime());
			}
		}
	}
	
	@Test
	public void listToString() {
		Map<String,String> map = new HashMap<>();
		map.put("q1", "a1");
		map.put("q2", "a2");
		
		List<String> abc = Arrays.asList("a","b","c");
		System.out.println(abc.toString());
		System.out.println(map.toString());
	}
	
	@Test
	public void writeTest() {
		WriteQuestionnaireReq req = new WriteQuestionnaireReq();
		
		req.setName("mike");
		req.setPhone("0912345678");
		req.setEmail("mike@gmail.com");
		req.setAge(33);
		req.setFinishTime(LocalDateTime.now());
		req.setGender("男");
		req.setQuestionnaireTitle("test3");
		
		Map<String, String>ansMap = new HashMap<>();
		ansMap.put("q1", "a1");
		ansMap.put("q2", "a3;a4");
		
		req.setUserAns(ansMap);
		QuestionsRes res = questionsService.writeQuestionnaire(req);
		System.out.println(res.getMessage());
	}
	
}
