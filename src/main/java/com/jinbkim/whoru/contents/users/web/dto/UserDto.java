package com.jinbkim.whoru.contents.users.web.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;
    private String testId;
}
