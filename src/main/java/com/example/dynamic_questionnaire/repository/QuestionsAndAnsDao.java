package com.example.dynamic_questionnaire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dynamic_questionnaire.entity.QuestionsAndAns;

@Repository
public interface QuestionsAndAnsDao extends JpaRepository<QuestionsAndAns, Integer>{

	public QuestionsAndAns findByQuestionnaireTitleAndQuestions(String title,String questions);
	
	public List<QuestionsAndAns> findByQuestionnaireTitle(String title);
}
