package com.jinbkim.whoru.contents.questionlist.web.dto;

import com.jinbkim.whoru.contents.questionlist.domain.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionSolveDto {

    private Question question;
    private Integer currentPage;
    private Integer totalPage;
    private Integer prevPage;
    private Integer nextPage;
    private Boolean isLastPage;


    // constructor
    @Builder
    public QuestionSolveDto(Question question, Integer currentPage, Integer totalPage) {
        this.question = question;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.prevPage = null;
        this.nextPage = null;
        this.isLastPage = null;
    }
}
