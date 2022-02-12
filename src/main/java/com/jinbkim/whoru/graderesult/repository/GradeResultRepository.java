package com.jinbkim.whoru.graderesult.repository;

import com.jinbkim.whoru.graderesult.domain.GradeResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GradeResultRepository extends MongoRepository<GradeResult, String> {

}
