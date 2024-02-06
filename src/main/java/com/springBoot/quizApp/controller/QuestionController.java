package com.springBoot.quizApp.controller;

import com.springBoot.quizApp.exception.QuestionErrorResponse;
import com.springBoot.quizApp.exception.QuestionNotFoundException;
import com.springBoot.quizApp.model.Question;
import com.springBoot.quizApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;
import java.util.List;

@RestController
@RequestMapping("questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public List<Question> allQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}/")
    public List<Question> questionByCategory(@PathVariable String category) {
        if (category == null || category.isEmpty()) {
            throw new QuestionNotFoundException("Category cannot be null or empty");
        }
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("addQuestion")
    public ResponseEntity<QuestionErrorResponse> addQuestion(@Valid @RequestBody Question question, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            QuestionErrorResponse errorResponse = new QuestionErrorResponse();
            errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            errorResponse.setMessage("Validation error: " + bindingResult.getFieldError().getDefaultMessage());
            errorResponse.setTimeStamp(System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new QuestionErrorResponse(
                200, "Question Created", System.currentTimeMillis()
        ), HttpStatus.OK);
    }
}
