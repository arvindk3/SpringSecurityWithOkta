package com.arv.QuestionService.controller;

import com.arv.QuestionService.entities.Question;
import com.arv.QuestionService.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {


    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

@PostMapping
    public Question create(@RequestBody Question question){
        return  questionService.create(question);
    }

    @GetMapping
    public List<Question> getAll(){
        return questionService.getAll();
    }

    @GetMapping("/{id}")
    public Question get(@PathVariable Long id){

        return questionService.get(id);
    }

    @GetMapping("/quiz/{quizId}")
    public  List<Question> getQuestionsOfQuiz(@PathVariable Long quizId){
return questionService.getQuestionsOfQuiz(quizId);
    }
}
