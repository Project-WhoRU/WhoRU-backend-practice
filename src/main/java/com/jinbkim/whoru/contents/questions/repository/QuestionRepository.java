package com.jinbkim.whoru.contents.questions.repository;

import com.jinbkim.whoru.contents.questions.domain.question.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question, String> {
}
