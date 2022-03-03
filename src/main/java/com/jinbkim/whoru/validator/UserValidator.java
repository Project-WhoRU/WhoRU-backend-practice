package com.jinbkim.whoru.validator;

import com.jinbkim.whoru.contents.users.repository.UserRepository;
import com.jinbkim.whoru.contents.users.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto user = (UserDto) target;

        if (userRepository.existsByNickname(user.getNickname()))
            errors.rejectValue("nickname", "duplicatedNickname", null);
        if (user.getNickname().length() < 6 || user.getNickname().length() > 12 || !org.apache.commons.lang3.StringUtils.isAlphanumeric(user.getNickname()))  // 아이디 : 6-12자 이내 영문, 숫자 사용 가능
            errors.rejectValue("nickname", "wrongNickname", null);
        if (user.getPassword().length() < 8 || user.getPassword().length() > 20)  // 비밀번호 : 8~20자 이상 문자, 숫자, 기호 사용 가능
            errors.rejectValue("password", "wrongPassword", null);
    }
}
