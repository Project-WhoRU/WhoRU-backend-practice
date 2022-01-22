package com.jinbkim.whoru.exception.customexceptions;

import com.jinbkim.whoru.exception.error.ErrorCode;
import lombok.Getter;

@Getter
public class AnswerSizeIsWrongException extends CustomException {
    private static final long serialVersionUID = 1L;

    public AnswerSizeIsWrongException() {
        super(ErrorCode.ANSWER_SIZE_IS_WRONG);
    }
}
