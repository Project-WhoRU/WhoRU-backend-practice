package com.jinbkim.whoru.tests.web.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TestSetNicknameRequestDto {
    @NotNull(message = "nickname이 없습니다")
    private String nickname;
}