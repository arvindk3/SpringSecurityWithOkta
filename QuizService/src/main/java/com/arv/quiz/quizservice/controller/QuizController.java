package com.arv.quiz.quizservice.controller;

import com.arv.quiz.quizservice.entities.Question;
import com.arv.quiz.quizservice.entities.Quiz;
import com.arv.quiz.quizservice.service.QuizService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    private QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }
    //create
    @PostMapping
    public Quiz create(@RequestBody Quiz quiz){
        return quizService.add(quiz);
    }

   // int retry=1;
    @GetMapping
    @Retry(name="quizQuestionAll",fallbackMethod = "quizQuestionFallbackMethodAll")
    //@RateLimiter(name="quizQuestionRateLimiter",fallbackMethod = "quizQuestionFallbackMethodAll")
    public List<Quiz> get(){
    //    System.out.println("retry ::" +retry);
     //   retry++;
        return quizService.get();
    }

    //Fallback method for retry mechanism
    public List<Quiz> quizQuestionFallbackMethodAll(Exception e){

        System.out.println("FAllBack is executed for retry mechanism because service is down: "+e.getMessage());
e.printStackTrace();
        List<Question> javaQuestions=new ArrayList<>();
        javaQuestions.add(new Question(1L,"What is Java",2L));
        javaQuestions.add(new Question(2L,"What is Polymorphism",3L));
        List<Question> goLangQuestions=new ArrayList<>();
        goLangQuestions.add(new Question(1L,"What is GoLang",2L));
        List<Quiz> quiz=new ArrayList<>();
        quiz.add(new Quiz(1L,"dummy Question for Java",javaQuestions));
        quiz.add(new Quiz(1L,"dummy Question for GoLang",goLangQuestions));


        System.out.println(quiz);
        return quiz;
    }


    @GetMapping("/{id}")
    @CircuitBreaker(name="quizQuestion",fallbackMethod = "quizQuestionFallbackMethod")
    public Quiz getOne(@PathVariable Long id){
        return quizService.get(id);
    }

    //Creating fall back method for circuitbreaker
    public Quiz quizQuestionFallbackMethod( Long id,Exception ex){
        System.out.println("FAllBack is executed for circuit breaker because service is down: "+ex.getMessage());
        Question question= new Question();
        List<Question> questions=new ArrayList<>();
        questions.add(new Question(1L,"What is Java",2L));
        questions.add(new Question(2L,"What is Polymorphism",3L));
        Quiz quiz=new Quiz(1L,"dummy Title",questions);

        System.out.println(quiz);


        return quiz;
    }



  /*  public ResponseEntity<Quiz> quizQuestionFallbackMethod1(Long id, Exception ex){
        System.out.println("FAllBack is executed becauze service is down: "+ex.getMessage());
        Question question= new Question(1L,"What is Java",2L);
        question=new Question(2L,"What is Polymorphism",3L);
        List<Question> questions=new ArrayList<>();
        questions.add(question);
        Quiz quiz=new Quiz(1L,"dummy Title",questions);

        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }*/

}
