package com.jinbkim.whoru.contents.graderesult.repository;

import com.jinbkim.whoru.contents.graderesult.domain.GradeResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GradeResultRepository extends MongoRepository<GradeResult, String> {

}
