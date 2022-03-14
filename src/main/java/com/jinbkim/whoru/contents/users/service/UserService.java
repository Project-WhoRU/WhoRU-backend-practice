package com.jinbkim.whoru.contents.users.service;

import com.jinbkim.whoru.contents.tests.domain.Tests;
import com.jinbkim.whoru.contents.users.domain.Users;
import com.jinbkim.whoru.contents.users.domain.UsersImplement;
import com.jinbkim.whoru.contents.users.domain.UsersBucket;
import com.jinbkim.whoru.contents.users.repository.UserBucketRepository;
import com.jinbkim.whoru.contents.users.repository.UserRepository;
import com.jinbkim.whoru.contents.users.web.dto.LoginDto;
import com.jinbkim.whoru.contents.users.web.dto.SignUpDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserBucketRepository userBucketRepository;

//    public UsersImplement addUser(SignUpDto user, Tests tests) {
//        UsersImplement users = UsersImplement.builder()
//            .nickname(user.getNickname())
//            .password(user.getPassword())
//            .testId(tests.getId())
//            .build();
//        userRepository.save(users);
//        return users;
//    }

    public Users addUserBucket(SignUpDto user, Tests tests) {
        UsersBucket users = UsersBucket.builder()
            .nickname(user.getNickname())
            .password(user.getPassword1())
            .testId(tests.getId())
            .build();
        userBucketRepository.save(users);
        return users;
    }

//    public Users addUserBucket(LoginDto user) {
//        UsersBucket users = UsersBucket.builder()
//            .nickname(user.getNickname())
//            .password(user.getPassword())
//            .build();
//        userBucketRepository.save(users);
//        return users;
//    }

    public void addUser(Users users) {
        UsersImplement user = UsersImplement.builder()
            .id(users.getId())
            .nickname(users.getNickname())
            .password(users.getPassword())
            .testId(users.getTestId())
            .build();
        userRepository.save(user);
    }

    public boolean isLogin(LoginDto user) {
        UsersImplement users = this.userRepository.findByNickname(user.getNickname());
        if (users != null && users.getNickname().equals(user.getNickname()) && users.getPassword().equals(user.getPassword()))
            return true;
        return false;
    }

}