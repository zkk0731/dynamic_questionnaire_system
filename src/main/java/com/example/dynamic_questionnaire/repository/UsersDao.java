package com.example.dynamic_questionnaire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dynamic_questionnaire.entity.Users;

@Repository
public interface UsersDao extends JpaRepository<Users, Integer>{

	public List<Users> findByQuestionnaireTitle(String questionnaireTitle);
}
