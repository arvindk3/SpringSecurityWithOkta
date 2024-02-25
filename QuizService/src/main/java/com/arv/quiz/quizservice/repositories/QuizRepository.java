package com.arv.quiz.quizservice.repositories;

import com.arv.quiz.quizservice.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
}
