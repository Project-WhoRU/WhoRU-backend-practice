package com.jinbkim.whoru.questions.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class QuestionDto {
    private String question;
    private List<String> examples;
    private int answer;
}
