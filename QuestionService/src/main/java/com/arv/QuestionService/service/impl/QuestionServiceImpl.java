package com.arv.QuestionService.service.impl;

import com.arv.QuestionService.entities.Question;
import com.arv.QuestionService.repositories.QuestionRepositories;
import com.arv.QuestionService.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepositories questionRepositories;

    public QuestionServiceImpl(QuestionRepositories questionRepositories) {
        this.questionRepositories = questionRepositories;
    }

    @Override
    public Question create(Question question) {
        return questionRepositories.save(question);
    }

    @Override
    public List<Question> getAll() {
        return questionRepositories.findAll();
    }

    @Override
    public Question get(Long id) {
        return questionRepositories.findById(id).orElseThrow(()->new RuntimeException("no Question Found"));
    }

    @Override
    public List<Question> getQuestionsOfQuiz(Long quizId) {
        return questionRepositories.findByQuizId(quizId);
    }
}
