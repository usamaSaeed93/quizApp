package com.springBoot.quizApp.dao;

import com.springBoot.quizApp.model.Question;
import com.springBoot.quizApp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizDao extends JpaRepository<Quiz, Integer> {

    Quiz findByTitle(String title);
}
