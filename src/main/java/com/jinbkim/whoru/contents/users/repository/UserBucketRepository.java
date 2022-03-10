package com.jinbkim.whoru.contents.users.repository;

import com.jinbkim.whoru.contents.users.domain.UsersBucket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserBucketRepository extends MongoRepository<UsersBucket, String> {
}
