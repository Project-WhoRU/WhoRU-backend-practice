package com.jinbkim.whoru.tests.web.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TestInitRequestDto {
    @NotNull(message = "nickname이 없음")
    private String nickname;
}
