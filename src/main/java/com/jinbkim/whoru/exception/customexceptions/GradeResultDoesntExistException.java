package com.jinbkim.whoru.exception.customexceptions;

import com.jinbkim.whoru.exception.error.ErrorCode;
import lombok.Getter;

@Getter
public class GradeResultDoesntExistException extends CustomException {
    private static final long serialVersionUID = 1L;

    public GradeResultDoesntExistException() {
        super(ErrorCode.GRADE_RESULT_DOESNT_EXIST);
    }
}