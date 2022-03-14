package com.jinbkim.whoru.contents.users.repository;

import com.jinbkim.whoru.contents.users.domain.UsersImplement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UsersImplement, String> {
    boolean existsByNickname(String nickname);
    UsersImplement findByNickname(String nickname);
}
