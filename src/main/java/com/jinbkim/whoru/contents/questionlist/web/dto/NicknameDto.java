package com.jinbkim.whoru.contents.questionlist.web.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NicknameDto {

    @NotBlank
    private String nickname;
}