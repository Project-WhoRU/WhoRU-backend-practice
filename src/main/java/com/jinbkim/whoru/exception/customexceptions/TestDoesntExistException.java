package com.jinbkim.whoru.exception.customexceptions;

import com.jinbkim.whoru.exception.error.ErrorCode;
import lombok.Getter;

@Getter
public class TestDoesntExistException extends CustomException {
    private static final long serialVersionUID = 1L;

    public TestDoesntExistException() {
        super(ErrorCode.TEST_DOESNT_EXIST);
    }
}