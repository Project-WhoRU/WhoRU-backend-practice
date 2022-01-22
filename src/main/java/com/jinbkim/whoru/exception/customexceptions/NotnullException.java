package com.jinbkim.whoru.exception.customexceptions;

import com.jinbkim.whoru.exception.error.ErrorCode;
import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class NotnullException extends CustomException {
    private static final long serialVersionUID = 1L;
    private Errors errors;

    public NotnullException(Errors errors) {
        super(ErrorCode.FOUND_NULL_VALUE);
        this.errors = errors;
    }

}
