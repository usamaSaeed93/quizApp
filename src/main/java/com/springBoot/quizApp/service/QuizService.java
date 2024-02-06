package com.springBoot.quizApp.service;

import com.springBoot.quizApp.dao.QuestionDAO;
import com.springBoot.quizApp.dao.QuizDao;
import com.springBoot.quizApp.model.AnswerRequest;
import com.springBoot.quizApp.model.Question;
import com.springBoot.quizApp.model.Quiz;
import com.springBoot.quizApp.model.QuizWrapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDAO questionDAO;

    @Transactional
    public ResponseEntity<String> createQuiz(String category, String title, int numQ) {
        try {
            List<Question> randomQuestions = questionDAO.findQuestionsByRandomCategory(category, numQ);

            if (randomQuestions.isEmpty()) {
                return new ResponseEntity<>("No questions found for the specified category.", HttpStatus.NOT_FOUND);
            }

            Quiz quiz = new Quiz();
            quiz.setQuestions(randomQuestions);
            quiz.setTitle(title);
            quizDao.save(quiz);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while creating the quiz.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<QuizWrapper>> attemptQuiz(String title) {
        try {
            Quiz quiz = quizDao.findByTitle(title);

            if (quiz != null) {
                List<Question> questions = quiz.getQuestions();
                ArrayList<QuizWrapper> quizQuestions;

                if (!questions.isEmpty()) {
                    quizQuestions = new ArrayList<>(questions.size());

                    for (Question question : questions) {
                        QuizWrapper tempQuiz = new QuizWrapper();
                        tempQuiz.setId(question.getId());
                        tempQuiz.setQuestion(question.getQuestion());
                        tempQuiz.setOption1(question.getOption1());
                        tempQuiz.setOption2(question.getOption2());
                        tempQuiz.setOption3(question.getOption3());
                        tempQuiz.setOption4(question.getOption4());
                        quizQuestions.add(tempQuiz);
                    }
                } else {
                    quizQuestions = new ArrayList<>();
                }

                return new ResponseEntity<>(quizQuestions, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<String> checkAnswer(String answer, int id) {
        try {
            Question question = questionDAO.getById(id);
            String rightAnswer = null;

            if (question != null) {
                rightAnswer = question.getRightAnswer();
                if (rightAnswer.equals(answer)) {
                    return new ResponseEntity<>("The Answer is Correct, Congrats", HttpStatus.OK);
                }
            }

            return new ResponseEntity<>("The Answer is Wrong, correct answer is : " + rightAnswer, HttpStatus.I_AM_A_TEAPOT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while checking the answer.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> checkBulk(List<AnswerRequest> answerRequests) {
        try {
            int count = 0;
            for (AnswerRequest answerRequest : answerRequests) {
                Question question = questionDAO.getById(answerRequest.getId());
                if (question != null && question.getRightAnswer().equals(answerRequest.getAnswer())) {
                    count++;
                }
            }

            return new ResponseEntity<>("Champ, Your Score for this quiz is : " + count + " Out Of : " + answerRequests.size(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while checking the bulk answers.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    }
