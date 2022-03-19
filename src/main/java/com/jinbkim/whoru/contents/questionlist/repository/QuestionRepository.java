package com.jinbkim.whoru.contents.questionlist.repository;

import com.jinbkim.whoru.contents.questionlist.domain.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question, String> {

}
