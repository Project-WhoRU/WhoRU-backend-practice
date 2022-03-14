package com.jinbkim.whoru.contents.graderesult.repository;

import com.jinbkim.whoru.contents.graderesult.domain.GradeResult;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GradeResultRepository extends MongoRepository<GradeResult, String> {
    Optional<List<GradeResult>> findByRequestNickname(String nickname);
}
