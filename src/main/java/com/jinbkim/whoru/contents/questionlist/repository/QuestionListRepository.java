package com.jinbkim.whoru.contents.questionlist.repository;

import com.jinbkim.whoru.contents.questionlist.domain.QuestionList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionListRepository extends MongoRepository<QuestionList, String> {
}
