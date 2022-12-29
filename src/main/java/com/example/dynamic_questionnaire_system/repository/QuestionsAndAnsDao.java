package com.example.dynamic_questionnaire_system.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.example.dynamic_questionnaire_system.entity.QuestionsAndAns;

@Repository
public interface QuestionsAndAnsDao extends JpaRepository<QuestionsAndAns, Integer>{

	public QuestionsAndAns findByQuestionnaireTitleAndQuestions(String title,String questions);
	
	public List<QuestionsAndAns> findByQuestionnaireTitle(String title);
	
	@Transactional
	@Modifying
	public void deleteByQuestionnaireTitleIn(List<String> questionnaireTitleList);
	
	@Transactional
	@Modifying
	public void deleteByIdIn(List<Integer> ids);
}
