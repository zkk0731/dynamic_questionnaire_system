package com.example.dynamic_questionnaire_system.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dynamic_questionnaire_system.entity.Questionnaire;

@Repository
public interface QuestionnaireDao extends JpaRepository<Questionnaire, Integer>{

	public Questionnaire findByTitle(String title);
	
	public List<Questionnaire> findByTitleContaining(String title);
	
	public void deleteByTitleIn(List<String> titleList);
	
	public List<Questionnaire> findByTitleContainingAndStartTimeGreaterThanEqualAndEndTimeLessThanEqualOrderByIdDesc(String title, LocalDate startTime, LocalDate endTime);
	
	public boolean existsByTitle(String title);
	
	public List<Questionnaire> findAllByOrderByIdDesc();
	
}
