package com.springBoot.quizApp.dao;

import com.springBoot.quizApp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionDAO extends JpaRepository<Question, Integer> {
  public List<Question> findByCategory(String category);

  @Query(value = "SELECT * FROM questions q Where q.category=:category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
  List<Question> findQuestionsByRandomCategory(String category, int numQ);
}
