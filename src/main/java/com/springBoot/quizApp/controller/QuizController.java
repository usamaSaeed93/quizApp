package com.springBoot.quizApp.controller;

import com.springBoot.quizApp.model.AnswerRequest;
import com.springBoot.quizApp.model.QuizWrapper;
import com.springBoot.quizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("createQuiz")
    public ResponseEntity<String> createQuiz(
            @RequestParam String category,
            @RequestParam String title,
            @RequestParam int numQ) {
        return quizService.createQuiz(category, title, numQ);
    }

    @GetMapping("attemptQuiz")
    public ResponseEntity<List<QuizWrapper>> attemptQuiz(@RequestParam String title) {
        return quizService.attemptQuiz(title);

    }

    @PostMapping("checkAnswer")
    public ResponseEntity<String> checkAnswer(@RequestBody AnswerRequest answerRequest) {
        return quizService.checkAnswer(answerRequest.getAnswer(), answerRequest.getId());
    }

    @PostMapping("checkBulk")
    public ResponseEntity<String> checkBulk (@RequestBody List<AnswerRequest> answerRequests){
        return quizService.checkBulk(answerRequests);

    }
}
