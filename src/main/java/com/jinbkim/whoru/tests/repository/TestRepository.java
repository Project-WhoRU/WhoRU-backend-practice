package com.jinbkim.whoru.tests.repository;

import com.jinbkim.whoru.tests.domain.Tests;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<Tests, String> {
}
