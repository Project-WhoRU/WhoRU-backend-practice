package com.jinbkim.whoru.contents.graderesult.repository;

import com.jinbkim.whoru.contents.graderesult.domain.GradeResult;
import com.jinbkim.whoru.contents.users.domain.Users;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GradeResultRepository extends MongoRepository<GradeResult, String> {

    List<GradeResult> findByUsers(Users users);
}
