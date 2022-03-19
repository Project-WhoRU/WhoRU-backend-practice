package com.jinbkim.whoru.validator;

import com.jinbkim.whoru.contents.users.repository.UserRepository;
import com.jinbkim.whoru.contents.users.service.UserService;
import com.jinbkim.whoru.contents.users.web.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

// 로그인이 가능한지 검증
@Component
@RequiredArgsConstructor
public class LoginValidator implements Validator {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginDto user = (LoginDto) target;

        if (!userRepository.existsByNickname(user.getNickname())) {
            errors.rejectValue("nickname", "noNickname", null);
        }
        if (!userService.isLoginPossible(user)) {
            errors.rejectValue("password", "checkingPassword", null);
        }
    }
}
