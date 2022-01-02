package com.jinbkim.whoru.tests.repository;

import com.jinbkim.whoru.tests.domain.Test;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<Test, String> {
}
