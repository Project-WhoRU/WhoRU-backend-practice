package com.jinbkim.whoru.contents.tests.repository;

import com.jinbkim.whoru.contents.tests.domain.Tests;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<Tests, String> {
//    boolean existsByNickname(String nickname);
//    Tests findByNickname(String nickname);
}
