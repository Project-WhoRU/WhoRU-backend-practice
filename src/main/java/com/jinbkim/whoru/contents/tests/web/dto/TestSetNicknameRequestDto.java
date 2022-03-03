package com.jinbkim.whoru.contents.tests.web.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TestSetNicknameRequestDto {
    @NotBlank
    private String nickname;
}