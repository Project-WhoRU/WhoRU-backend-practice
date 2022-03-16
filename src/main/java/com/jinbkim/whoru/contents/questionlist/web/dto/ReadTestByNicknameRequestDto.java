package com.jinbkim.whoru.contents.questionlist.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ReadTestByNicknameRequestDto {
    private String nicknameSearch;
}
