package com.jinbkim.whoru.exception.customexceptions;

import com.jinbkim.whoru.exception.error.ErrorCode;
import lombok.Getter;

@Getter
public class QuestionDoesntExistException extends CustomException {
    private static final long serialVersionUID = 1L;

    public QuestionDoesntExistException() {
        super(ErrorCode.QUESTION_DOESNT_EXIST);
    }
}