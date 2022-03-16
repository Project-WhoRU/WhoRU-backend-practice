package com.jinbkim.whoru.contents.users.repository;

import com.jinbkim.whoru.contents.users.domain.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Users, String> {
    boolean existsByNickname(String nickname);
    Users findByNickname(String nickname);
}
