package com.jinbkim.whoru.questions.repository;

import com.jinbkim.whoru.questions.domain.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question, String> {
}
