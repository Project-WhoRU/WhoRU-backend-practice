package com.jinbkim.whoru.contents.questionlist.web.dto;

import com.jinbkim.whoru.contents.questionlist.domain.Examples;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class QuestionDto {

    @NotBlank
    private String type;
    @NotBlank
    private String question;
    private Examples examples;
    @NotBlank
    private String answer;
    private boolean complete;


    // constructor
    @Builder
    public QuestionDto(String type, String question, Examples examples, String answer,
        boolean complete) {
        this.type = type;
        this.question = question;
        this.examples = examples;
        this.answer = answer;
        this.complete = complete;
    }
}
