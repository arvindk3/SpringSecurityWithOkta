package com.arv.quiz.quizservice.service;

import com.arv.quiz.quizservice.entities.Quiz;

import java.util.List;

public interface QuizService {

    Quiz add(Quiz quiz);
    List<Quiz> get();
    Quiz get(Long id);
}
