package com.arv.quiz.quizservice.service.impl;

import com.arv.quiz.quizservice.entities.Quiz;
import com.arv.quiz.quizservice.repositories.QuizRepository;
import com.arv.quiz.quizservice.service.QuestionClient;
import com.arv.quiz.quizservice.service.QuizService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServicesImpl implements QuizService {

   private QuizRepository quizRepository;

   private QuestionClient questionClient;

    public QuizServicesImpl(QuizRepository quizRepository, QuestionClient questionClient) {
        this.quizRepository = quizRepository;
        this.questionClient = questionClient;
    }

    @Override
    public Quiz add(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> get() {
        List<Quiz> quizzes = quizRepository.findAll();
        List<Quiz> newQuizList = quizzes.stream().map(quiz -> {
            quiz.setQuestions(questionClient.getQuestionsOfQuiz(quiz.getId()));
            return quiz;
        }).toList();
        return newQuizList;
    }

    @Override
    public Quiz get(Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz nor found"));
       quiz.setQuestions(questionClient.getQuestionsOfQuiz(quiz.getId()));
        return quiz;
    }
}
