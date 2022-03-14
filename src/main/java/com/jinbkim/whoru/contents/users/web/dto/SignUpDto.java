package com.jinbkim.whoru.contents.users.web.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class SignUpDto {
    @NotBlank
    private String nickname;
    @NotBlank
    private String password1;
    private String password2;
}