package com.jinbkim.whoru.contents.users.service;

import com.jinbkim.whoru.contents.tests.domain.Tests;
import com.jinbkim.whoru.contents.users.domain.Users;
import com.jinbkim.whoru.contents.users.repository.UserRepository;
import com.jinbkim.whoru.contents.users.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Users addUser(UserDto user, Tests tests) {
        Users users = Users.builder()
            .nickname(user.getNickname())
            .password(user.getPassword())
            .testId(tests.getId())
            .build();
        userRepository.save(users);
        return users;
    }
}